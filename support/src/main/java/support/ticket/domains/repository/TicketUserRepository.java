package support.ticket.domains.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import support.ticket.domains.TicketUser;

@ApplicationScoped
public class TicketUserRepository implements PanacheRepository<TicketUser> {
}
