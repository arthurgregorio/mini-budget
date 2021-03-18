package br.eti.arthurgregorio.minibudget.application.converters;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementPayload;
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
public class MovementPayloadToMovement implements Converter<MovementPayload, Movement> {

    private final ContactRepository contactRepository;
    private final ClassificationRepository classificationRepository;

    public MovementPayloadToMovement(ContactRepository contactRepository, ClassificationRepository classificationRepository) {
        this.contactRepository = contactRepository;
        this.classificationRepository = classificationRepository;
    }

    @Override
    public Movement convert(MovementPayload movementPayload) {

        // FIXME if contactId is null then should save without link with a contact

        final var contact = this.loadContact(movementPayload.getContactId());
        final var classification = this.loadClassification(movementPayload.getClassificationId());

        return new Movement(movementPayload.getDescription(), movementPayload.getDueDate(), movementPayload.getPaymentDate(),
                movementPayload.getValue(), movementPayload.getState(), contact, classification);
    }

    private Contact loadContact(UUID contactId) {
        return this.contactRepository.findByExternalId(contactId)
                .orElseThrow(() -> new ContactNotFound(contactId.toString()));
    }

    private Classification loadClassification(UUID classificationId) {
        return this.classificationRepository.findByExternalId(classificationId)
                .orElseThrow(() -> new ClassificationNotFound(classificationId.toString()));
    }
}
