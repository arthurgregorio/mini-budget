package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassificationMapper extends MappingOperations<Classification, ClassificationPayload> {

    ClassificationMapper INSTANCE = Mappers.getMapper(ClassificationMapper.class);
}