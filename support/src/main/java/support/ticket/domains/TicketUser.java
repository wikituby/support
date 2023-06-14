package support.ticket.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class TicketUser extends PanacheEntity {
    @ManyToOne
    public Ticket ticket;

    @OneToOne
    public FileResource file;

    @Column
    public LocalDateTime creationDate = LocalDateTime.now();

    @Column
    public String description;

    @ManyToOne
    public User agentId;

    @Column
    public Integer rating;

}
