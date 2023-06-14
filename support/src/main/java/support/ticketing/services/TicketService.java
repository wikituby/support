package support.ticketing.services;

import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import support.configuration.handler.ResponseMessage;
import support.statics.AppTypes;
import support.ticket.domains.Category;
import support.ticket.domains.Ticket;
import support.ticket.domains.repository.CategoryRepository;
import support.ticket.domains.repository.TicketRepository;
import support.ticketing.services.payload.TicketRequest;
import support.ticketing.services.payload.TicketUpdateRequest;


import java.time.LocalDateTime;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.findById;

@ApplicationScoped
public class TicketService {

    @Inject
    TicketRepository ticketRepository;

    @Inject
    CategoryRepository categoryRepository;

    public Ticket create(TicketRequest request, HttpServerRequest httpRequest, SecurityContext context){

        String ticketNumber = ticketRepository.generateTicketNumber(AppTypes.getEnum(context.getUserPrincipal().getName()).label);

        Category category = categoryRepository.getById(request.categoryId);

        Ticket ticket = new Ticket();
        ticket.number = ticketNumber;
        ticket.description = request.description;
        ticket.creationDate = LocalDateTime.now();
        ticket.applicationType = context.getUserPrincipal().getName();
        ticket.email =  context.getUserPrincipal().toString();
        ticket.userBusiness = request.userBusiness;
        ticket.userAgent = httpRequest.getHeader("user-agent");
        ticket.category = category;

        ticket.persist();

        return ticket;
    }


    public Ticket updateTicket(TicketUpdateRequest request, HttpServerRequest httpRequest, SecurityContext ctx) {

        return null;
    }
}
