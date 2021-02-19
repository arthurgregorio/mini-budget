package br.eti.arthurgregorio.minibudget.model.entities;

import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(callSuper = true)
@Table(name = "classifications")
@EqualsAndHashCode(callSuper = true)
public class Classification extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 7, nullable = false)
    private Type type;

    public void updateValues(ClassificationPayload payload) {
        this.name = payload.getName();
        this.type = payload.getType();
    }

    public enum Type {
        INCOME,
        EXPENSE
    }
}
