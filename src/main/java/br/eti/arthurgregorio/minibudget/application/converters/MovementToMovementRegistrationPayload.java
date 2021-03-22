package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementRegistrationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface MovementToMovementRegistrationPayload extends Converter<Movement, MovementRegistrationPayload> {

    @Override
    @Mapping(source = "externalId", target = "id")
    @Mapping(source = "contact.externalId", target = "contactId")
    @Mapping(source = "classification.externalId", target = "classificationId")
    MovementRegistrationPayload convert(Movement movement);
}
