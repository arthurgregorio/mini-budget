package br.eti.arthurgregorio.minibudget.application.payloads;

import br.eti.arthurgregorio.minibudget.application.validators.Unique;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@ToString
public class ContactPayload {

    @Getter
    private final UUID externalId;
    @Getter
    @NotBlank(message = "contact.email.is-blank")
    private final String name;
    @Getter
    @NotBlank(message = "contact.email.is-blank")
    @Unique(domainClass = Contact.class, attribute = "email", message = "contact.email.not-unique")
    private final String email;
    @Getter
    private final String telephone;

    @JsonCreator
    public ContactPayload(@JsonProperty("externalId") final UUID externalId,
                          @JsonProperty("name") final String name,
                          @JsonProperty("email") final String email,
                          @JsonProperty("telephone") final String telephone) {
        this.externalId = externalId;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }
}
