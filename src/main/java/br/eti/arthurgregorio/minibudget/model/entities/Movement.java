package br.eti.arthurgregorio.minibudget.model.entities;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementPayload;
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

    public Movement(String description, LocalDate dueDate, LocalDate paymentDate, BigDecimal value, State state,
                    Contact contact, Classification classification) {
        this.description = description;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.value = value;
        this.state = state;
        this.contact = contact;
        this.classification = classification;
    }

    public void updateValues(MovementPayload movementPayload) {
        this.description = movementPayload.getDescription();
        this.dueDate = movementPayload.getDueDate();
        this.value = movementPayload.getValue();
    }

    public enum State {
        PAID,
        OPEN
    }
}
