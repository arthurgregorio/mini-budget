package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.converters.mappers.ContactMapper;
import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactToContactPayloadConverter implements Converter<Contact, ContactPayload> {

    @Override
    public ContactPayload convert(Contact object) {
        return ContactMapper.INSTANCE.fromModel(object);
    }
}
