package br.eti.arthurgregorio.minibudget.application.payloads;

import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ToString
public class ClassificationPayload extends BasePayload {

    @Getter
    @NotBlank(message = "classification.name.is-blank")
    private final String name;
    @Getter
    @NotNull(message = "classification.type.is-null")
    private final Classification.Type type;

    @JsonCreator
    public ClassificationPayload(@JsonProperty("id") final UUID id,
                                 @JsonProperty("name") final String name,
                                 @JsonProperty("type") final Classification.Type type) {
        super(id);
        this.name = name;
        this.type = type;
    }
}
