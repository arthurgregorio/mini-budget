package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementRegistrationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.exceptions.ClassificationNotFound;
import br.eti.arthurgregorio.minibudget.model.exceptions.ContactNotFound;
import br.eti.arthurgregorio.minibudget.model.repositories.ClassificationRepository;
import br.eti.arthurgregorio.minibudget.model.repositories.ContactRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovementRegistrationPayloadToMovement implements Converter<MovementRegistrationPayload, Movement> {

    private final ContactRepository contactRepository;
    private final ClassificationRepository classificationRepository;

    public MovementRegistrationPayloadToMovement(ContactRepository contactRepository, ClassificationRepository classificationRepository) {
        this.contactRepository = contactRepository;
        this.classificationRepository = classificationRepository;
    }

    @Override
    public Movement convert(MovementRegistrationPayload payload) {

        final var contact = this.loadContact(payload.getContactId());
        final var classification = this.loadClassification(payload.getClassificationId());

        return new Movement(payload.getDescription(), payload.getDueDate(),
                payload.getValue(), contact, classification);
    }

    private Contact loadContact(UUID contactId) {
        if (contactId == null) {
            return null;
        }
        return this.contactRepository.findByExternalId(contactId)
                .orElseThrow(() -> new ContactNotFound(contactId.toString()));
    }

    private Classification loadClassification(UUID classificationId) {
        return this.classificationRepository.findByExternalId(classificationId)
                .orElseThrow(() -> new ClassificationNotFound(classificationId.toString()));
    }
}
