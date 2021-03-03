package br.eti.arthurgregorio.minibudget.application.payloads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@ToString
public class MovementPayload extends BasePayload {

    @Getter
    private final String description;
    @Getter
    private final LocalDate dueDate;
    @Getter
    private final BigDecimal value;
    @Getter
    private final UUID contactId;
    @Getter
    private final UUID classificationId;

    @JsonCreator
    public MovementPayload(@JsonProperty("id") final UUID id,
                           @JsonProperty("description") final String description,
                           @JsonProperty("dueDate") final LocalDate dueDate,
                           @JsonProperty("value") final BigDecimal value,
                           @JsonProperty("contactId") final UUID contactId,
                           @JsonProperty("classificationId") final UUID classificationId) {
        super(id);
        this.description = description;
        this.dueDate = dueDate;
        this.value = value;
        this.contactId = contactId;
        this.classificationId = classificationId;
    }
}
