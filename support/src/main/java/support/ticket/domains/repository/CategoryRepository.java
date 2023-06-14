package support.ticket.domains.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import support.ticket.domains.Category;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    public Category getById(Long id){
        return findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Not Found",404));
    }
}
