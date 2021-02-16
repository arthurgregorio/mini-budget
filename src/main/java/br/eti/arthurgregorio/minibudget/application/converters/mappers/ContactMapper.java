package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper extends MappingOperations<Contact, ContactPayload> {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);
}