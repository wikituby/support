package support.ticketing.services;

import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import support.configuration.security.JwtUtils;
import support.statics.AppTypes;
import support.ticket.domains.Category;
import support.ticket.domains.FileResource;
import support.ticket.domains.Ticket;
import support.ticket.domains.repository.CategoryRepository;
import support.ticket.domains.repository.TicketRepository;
import support.ticket.fileresource.service.FileService;
import support.ticketing.services.payload.TicketFilterRequest;
import support.ticketing.services.payload.TicketRequest;
import support.ticketing.services.payload.TicketUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.find;

@ApplicationScoped
public class TicketService {

    @Inject
    TicketRepository ticketRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    FileService fileService;

    @Inject
    JwtUtils jwtUtils;

    public Ticket create(TicketRequest request, HttpServerRequest httpRequest, SecurityContext context){

        String ticketNumber = ticketRepository.generateTicketNumber(AppTypes.getEnum(context.getUserPrincipal().getName()).label);

        Category category = categoryRepository.getById(request.categoryId);

        Ticket ticket = new Ticket();
        ticket.number = ticketNumber;
        ticket.description = request.description;
        ticket.creationDate = LocalDateTime.now();
        ticket.applicationType = context.getUserPrincipal().getName();
        ticket.email = jwtUtils.getJwt().getSubject();
        ticket.userBusiness = request.userBusiness;
        ticket.userAgent = httpRequest.getHeader("user-agent");
        ticket.category = category;
        if (request.fileRequest != null){
            ticket.file = fileService.create(request.fileRequest);
        }
        ticket.persist();
        return ticket;
    }
    public Ticket updateTicket(Long ticketId, TicketUpdateRequest request) {
        Optional<Ticket> optionalTicket = ticketRepository.findByIdOptional(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket;
            Category category = categoryRepository.getById(request.categoryId);
            ticket = optionalTicket.get();
            ticket.description = request.description;
            ticket.userBusiness = request.userBusiness;
            ticket.category = category;
            if (request.fileRequest != null) {
                ticket.file = fileService.create(request.fileRequest);
            }
            ticket.persist();
            return ticket;
        } else {
            throw new NotFoundException("Ticket not found with ID: " + ticketId);
        }
    }
    @Transactional
    public List<Ticket> findTicketsByFilter(TicketFilterRequest filterRequest) {
        return Ticket.find("userBusiness = ?1 AND email = ?2 AND applicationType = ?3",
                        filterRequest.userBusiness, filterRequest.email, filterRequest.applicationType)
                .list();
    }
}



