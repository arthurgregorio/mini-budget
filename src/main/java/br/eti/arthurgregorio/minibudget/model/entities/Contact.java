package br.eti.arthurgregorio.minibudget.model.entities;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contacts")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Contact extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @Getter
    @Setter
    @Column(name = "email", length = 150, nullable = false)
    private String email;
    @Getter
    @Setter
    @Column(name = "telephone", length = 20)
    private String telephone;

    public void updateValues(ContactPayload payload) {
        this.name = payload.getName();
        this.email = payload.getEmail();
        this.telephone = payload.getTelephone();
    }
}
