package br.eti.arthurgregorio.minibudget.application.controllers;

import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationQueryFilter;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import br.eti.arthurgregorio.minibudget.model.exceptions.ResourceNotFoundException;
import br.eti.arthurgregorio.minibudget.model.repositories.ClassificationRepository;
import br.eti.arthurgregorio.minibudget.model.services.ClassificationRegistrationService;
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
@RequestMapping("/api/classifications")
public class ClassificationController {

    private final ConversionService conversionService;

    private final ClassificationRepository classificationRepository;

    private final ClassificationRegistrationService classificationRegistrationService;

    public ClassificationController(ConversionService conversionService, ClassificationRepository classificationRepository,
                                    ClassificationRegistrationService classificationRegistrationService) {
        this.conversionService = conversionService;
        this.classificationRepository = classificationRepository;
        this.classificationRegistrationService = classificationRegistrationService;
    }

    @GetMapping
    public ResponseEntity<Page<ClassificationPayload>> get(ClassificationQueryFilter queryFilter, Pageable pageable) {

        final var pages = this.classificationRepository.findByFilter(queryFilter, pageable)
                .map(classification -> this.conversionService.convert(classification, ClassificationPayload.class));

        return checkIfOkOrNoContent(pages);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ClassificationPayload> getByExternalId(@PathVariable UUID externalId) {
        return this.classificationRepository.findByExternalId(externalId)
                .map(classification -> this.conversionService.convert(classification, ClassificationPayload.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<ClassificationPayload> create(@RequestBody @Valid ClassificationPayload payload) {

        final var classification = this.classificationRegistrationService.save(
                this.conversionService.convert(payload, Classification.class));

        final var newClassification = this.conversionService.convert(classification, ClassificationPayload.class);

        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(classification.getExternalId())
                .toUri();

        return ResponseEntity.created(location).body(newClassification);
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<ClassificationPayload> update(@PathVariable UUID externalId, @RequestBody @Valid ClassificationPayload payload) {

        final var classification = this.classificationRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(externalId));

        classification.updateValues(payload);

        final var updatedPayload = this.conversionService.convert(
                this.classificationRegistrationService.update(classification), ClassificationPayload.class);

        return ResponseEntity.ok(updatedPayload);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<?> delete(@PathVariable UUID externalId) {

        final var classification = this.classificationRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(externalId));

        this.classificationRegistrationService.delete(classification);

        return ResponseEntity.ok().build();
    }
}
