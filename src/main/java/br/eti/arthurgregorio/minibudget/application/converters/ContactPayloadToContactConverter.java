package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.converters.mappers.ContactMapper;
import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactPayloadToContactConverter implements Converter<ContactPayload, Contact> {

    @Override
    public Contact convert(ContactPayload payload) {
        return ContactMapper.INSTANCE.toModel(payload);
    }
}