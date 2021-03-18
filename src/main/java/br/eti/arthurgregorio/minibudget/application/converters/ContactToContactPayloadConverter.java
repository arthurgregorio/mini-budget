package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ContactToContactPayloadConverter extends Converter<Contact, ContactPayload> {

    @Override
    @Mapping(source = "externalId", target = "id")
    ContactPayload convert(Contact contact);
}
