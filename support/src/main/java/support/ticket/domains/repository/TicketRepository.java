package support.ticket.domains.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import support.ticket.domains.Ticket;
import support.ticketing.services.payload.TicketFilterRequest;

import java.util.List;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {

    public String generateTicketNumber(String appType){
        return find("applicationType LIKE ?1",appType+"%", Sort.descending("creationDate"))
                .firstResultOptional()
                .map(ticket -> generateNextNumber(appType,ticket.number))
                .orElse(String.format("%s%06d",appType, 1));
    }

    private String generateNextNumber(String appType,String lastTicketNumber){
        String number = lastTicketNumber.replaceAll("\\D", "");

        int nextNum = Integer.parseInt(number) + 1;
        return String.format("%s%06d",appType, nextNum);
    }

}
