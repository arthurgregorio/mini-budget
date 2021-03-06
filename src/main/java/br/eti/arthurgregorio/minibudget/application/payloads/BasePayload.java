package br.eti.arthurgregorio.minibudget.application.payloads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
public class BasePayload {

    @Getter
    private final UUID id;

    @JsonCreator
    public BasePayload(@JsonProperty("id") final UUID id) {
        this.id = id;
    }
}
