package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.converters.mappers.ClassificationMapper;
import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClassificationToClassificationPayloadConverter implements Converter<Classification, ClassificationPayload> {

    @Override
    public ClassificationPayload convert(Classification object) {
        return ClassificationMapper.INSTANCE.fromModel(object);
    }
}
