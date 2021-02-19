package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.converters.mappers.ClassificationMapper;
import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClassificationPayloadToClassificationConverter implements Converter<ClassificationPayload, Classification> {

    @Override
    public Classification convert(ClassificationPayload payload) {
        return ClassificationMapper.INSTANCE.toModel(payload);
    }
}