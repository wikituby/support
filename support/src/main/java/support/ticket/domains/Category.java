package support.ticket.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Category extends PanacheEntity {

    @Column
    public String name;

    @ManyToOne
    public Category parentCategory;


}
