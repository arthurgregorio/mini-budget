package br.eti.arthurgregorio.minibudget.model.entities;

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

    public void prepareForUpdate(Classification classification) {
        this.name = classification.getName();
        this.type = classification.getType();
    }

    public enum Type {
        INCOME,
        EXPENSE
    }
}
