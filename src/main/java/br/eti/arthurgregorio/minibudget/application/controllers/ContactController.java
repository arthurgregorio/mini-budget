package br.eti.arthurgregorio.minibudget.application.controllers;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.application.payloads.ContactQueryFilter;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import br.eti.arthurgregorio.minibudget.model.exceptions.ResourceNotFoundException;
import br.eti.arthurgregorio.minibudget.model.repositories.ContactRepository;
import br.eti.arthurgregorio.minibudget.model.services.ContactRegistrationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

import static br.eti.arthurgregorio.minibudget.application.ControllerUtilities.checkIfOkOrNoContent;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ConversionService conversionService;

    private final ContactRepository contactRepository;

    private final ContactRegistrationService contactRegistrationService;

    public ContactController(ConversionService conversionService, ContactRepository contactRepository,
                             ContactRegistrationService contactRegistrationService) {
        this.conversionService = conversionService;
        this.contactRepository = contactRepository;
        this.contactRegistrationService = contactRegistrationService;
    }

    @GetMapping
    public ResponseEntity<Page<ContactPayload>> get(ContactQueryFilter queryFilter, Pageable pageable) {

        final var pages = this.contactRepository.findByFilter(queryFilter, pageable)
                .map(contact -> this.conversionService.convert(contact, ContactPayload.class));

        return checkIfOkOrNoContent(pages);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ContactPayload> getById(@PathVariable UUID externalId) {
        return this.contactRepository.findByExternalId(externalId)
                .map(contact -> this.conversionService.convert(contact, ContactPayload.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<ContactPayload> create(@RequestBody @Valid ContactPayload payload) {

        final var contact = this.contactRegistrationService.create(
                this.conversionService.convert(payload, Contact.class));

        final var newContact = this.conversionService.convert(contact, ContactPayload.class);

        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contact.getId())
                .toUri();

        return ResponseEntity.created(location).body(newContact);
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<ContactPayload> update(@PathVariable UUID externalId, @RequestBody @Valid ContactPayload payload) {

        final var contact = this.contactRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(externalId));

        contact.updateValues(payload);

        final var updatedPayload = this.conversionService.convert(
                this.contactRegistrationService.update(contact), ContactPayload.class);

        return ResponseEntity.ok(updatedPayload);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<?> delete(@PathVariable UUID externalId) {

        final var contact = this.contactRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(externalId));

        this.contactRegistrationService.delete(contact);

        return ResponseEntity.ok().build();
    }
}
