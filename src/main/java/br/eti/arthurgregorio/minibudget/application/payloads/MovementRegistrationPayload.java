package br.eti.arthurgregorio.minibudget.application.payloads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@ToString
public class MovementRegistrationPayload extends BasePayload {

    @Getter
    @NotBlank(message = "movement.description.is-blank")
    private final String description;
    @Getter
    @NotNull(message = "movement.due-date.is-null")
    private final LocalDate dueDate;
    @Getter
    @NotNull(message = "movement.value.is-null")
    @Min(value = 1, message = "movement.value.is-lt-or-eq-zero")
    private final BigDecimal value;

    @Getter
    private final UUID contactId;
    @Getter
    @NotNull(message = "movement.classification.is-null")
    private final UUID classificationId;

    @JsonCreator
    public MovementRegistrationPayload(@JsonProperty("id") final UUID id,
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
