package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingOperations.class)
public interface ClassificationMapper {

    ClassificationMapper INSTANCE = Mappers.getMapper(ClassificationMapper.class);

    @InheritConfiguration
    ClassificationPayload fromModel(Classification model);

    @InheritInverseConfiguration(name = "defaultFromModel")
    Classification toModel(ClassificationPayload payload);
}