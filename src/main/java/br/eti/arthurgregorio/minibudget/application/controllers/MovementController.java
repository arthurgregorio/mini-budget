package br.eti.arthurgregorio.minibudget.application.controllers;

import br.eti.arthurgregorio.minibudget.application.payloads.MovementPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
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
    public ResponseEntity<MovementPayload> getByExternalId(@PathVariable UUID externalId) {
        return this.movementRepository.findByExternalId(externalId)
                .map(movement -> this.conversionService.convert(movement, MovementPayload.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<MovementPayload> create(@RequestBody @Valid MovementPayload payload) {

        final var movement = this.movementRegistrationService.save(
                this.conversionService.convert(payload, Movement.class));

        final var newMovement = this.conversionService.convert(movement, MovementPayload.class);

        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(movement.getExternalId())
                .toUri();

        return ResponseEntity.created(location).body(newMovement);
    }
}
