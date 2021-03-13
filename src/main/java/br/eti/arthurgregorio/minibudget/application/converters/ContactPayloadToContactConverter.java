package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ContactPayloadToContactConverter extends Converter<ContactPayload, Contact> {

    @Override
    @Mapping(source = "id", target = "externalId")
    Contact convert(ContactPayload payload);
}