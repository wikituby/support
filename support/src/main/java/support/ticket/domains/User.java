package support.ticket.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class User extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public  String username;

    @Column(unique = true,nullable = false)
    public String email;

    @JsonbTransient
    @Column(nullable = false)
    public String password;

    @Column
    public String role;
}
