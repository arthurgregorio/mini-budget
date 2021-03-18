package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ClassificationPayloadToClassificationConverter extends Converter<ClassificationPayload, Classification> {

    @Override
    @Mapping(source = "id", target = "externalId")
    Classification convert(ClassificationPayload classificationPayload);
}