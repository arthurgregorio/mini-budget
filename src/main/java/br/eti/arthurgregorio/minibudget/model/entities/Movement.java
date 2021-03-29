package br.eti.arthurgregorio.minibudget.model.entities;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementRegistrationPayload;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movements")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Movement extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    @Getter
    @Setter
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Getter
    @Setter
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    @Getter
    @Setter
    @Column(name = "value", nullable = false)
    private BigDecimal value;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 4)
    private State state;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_contact")
    private Contact contact;
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_classification", nullable = false)
    private Classification classification;

    public Movement() {
        this.state = State.OPEN;
    }

    public Movement(String description, LocalDate dueDate, BigDecimal value, Contact contact, Classification classification) {
        this();
        this.description = description;
        this.dueDate = dueDate;
        this.value = value;
        this.contact = contact;
        this.classification = classification;
    }

    public void prepareForUpdate(Movement movement) {

        this.description = movement.getDescription();
        this.dueDate = movement.getDueDate();
        this.value = movement.getValue();

        this.contact = movement.getContact();
        this.classification = movement.getClassification();
    }

    public enum State {
        PAID,
        OPEN
    }
}
