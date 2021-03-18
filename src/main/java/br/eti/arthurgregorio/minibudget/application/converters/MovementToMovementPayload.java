package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface MovementToMovementPayload extends Converter<Movement, MovementPayload> {

    @Override
    @Mapping(source = "externalId", target = "id")
    @Mapping(source = "contact.externalId", target = "contactId")
    @Mapping(source = "classification.externalId", target = "classificationId")
    MovementPayload convert(Movement movement);
}
