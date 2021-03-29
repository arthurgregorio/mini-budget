package br.eti.arthurgregorio.minibudget.application.controllers;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementRegistrationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.exceptions.ResourceNotFoundException;
import br.eti.arthurgregorio.minibudget.model.repositories.MovementRepository;
import br.eti.arthurgregorio.minibudget.model.services.MovementRegistrationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final ConversionService conversionService;

    private final MovementRepository movementRepository;

    private final MovementRegistrationService movementRegistrationService;

    public MovementController(ConversionService conversionService,
                              MovementRepository movementRepository,
                              MovementRegistrationService movementRegistrationService) {
        this.conversionService = conversionService;
        this.movementRepository = movementRepository;
        this.movementRegistrationService = movementRegistrationService;
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<MovementRegistrationPayload> getByExternalId(@PathVariable UUID externalId) {
        return this.movementRepository.findByExternalId(externalId)
                .map(movement -> this.conversionService.convert(movement, MovementRegistrationPayload.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<MovementRegistrationPayload> create(@RequestBody @Valid MovementRegistrationPayload payload) {

        final var movement = this.movementRegistrationService.save(
                this.conversionService.convert(payload, Movement.class));

        final var newMovement =
                this.conversionService.convert(movement, MovementRegistrationPayload.class);

        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(movement.getExternalId())
                .toUri();

        return ResponseEntity.created(location).body(newMovement);
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<MovementRegistrationPayload> update(@PathVariable UUID externalId,
                                                              @RequestBody @Valid MovementRegistrationPayload payload) {

        final var movement = this.movementRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(externalId));

        movement.prepareForUpdate(this.conversionService.convert(payload, Movement.class));

        final var updatedPayload = this.conversionService.convert(
                this.movementRegistrationService.update(movement), MovementRegistrationPayload.class);

        return ResponseEntity.ok(updatedPayload);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<?> delete(@PathVariable UUID externalId) {

        final var movement = this.movementRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(externalId));

        this.movementRegistrationService.delete(movement);

        return ResponseEntity.ok().build();
    }
}
