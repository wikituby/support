package support.ticket.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import support.statics.StatusTypes;

import java.time.LocalDateTime;

@Entity
public class Ticket extends PanacheEntity {

    @Column(nullable = false)
    public String number;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public String status = StatusTypes.CREATED.label;

    @Column
    public String email;

    @Column
    public String userBusiness;

    @Column
    public String applicationType;

    @Column
    public LocalDateTime creationDate;

    @Column
    public LocalDateTime updateDate = LocalDateTime.now();

    @ManyToOne
    public User supportAgent;

    @ManyToOne
    public FileResource file;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Category category;

    @Column
    public String version;

    @Column
    public String userAgent;

}
