package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingOperations.class)
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @InheritConfiguration
    ContactPayload fromModel(Contact model);

    @InheritInverseConfiguration(name = "defaultFromModel")
    Contact toModel(ContactPayload payload);
}