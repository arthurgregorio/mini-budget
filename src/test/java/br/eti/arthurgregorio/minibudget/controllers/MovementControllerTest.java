package br.eti.arthurgregorio.minibudget.controllers;

import br.eti.arthurgregorio.minibudget.AbstractControllerTest;
import br.eti.arthurgregorio.minibudget.application.payloads.MovementRegistrationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.repositories.MovementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovementControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/movements";

    @Value("classpath:/payloads/movement/create.json")
    private Resource newMovementJson;
    @Value("classpath:/payloads/movement/update.json")
    private Resource updateMovementJson;

    @Autowired
    private MovementRepository movementRepository;

    @Test
    void shouldSaveMovement() throws Exception {

        final var movementJson = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "df9b84b3-5857-4cf9-be19-0936f7e5219c")
                .replace("{classificationId}", "df9b84b3-5857-4cf9-be19-0936f7e5219c");

        final var movementPayload = performPostAndExpectCreated(BASE_URL, movementJson,
                MovementRegistrationPayload.class);

        assertThat(movementPayload).isNotNull();
        assertThat(movementPayload.getId()).isNotNull();

        final var optionalMovement = this.movementRepository.findByExternalId(movementPayload.getId());
        assertThat(optionalMovement).isPresent();

        final var movementFromBd = optionalMovement.get();
        assertThat(movementFromBd.getExternalId()).isEqualTo(movementPayload.getId());
        assertThat(movementFromBd.getDescription()).isEqualTo(movementPayload.getDescription());
        assertThat(movementFromBd.getDueDate()).isEqualTo(movementPayload.getDueDate());
        assertThat(movementFromBd.getValue()).isEqualTo(movementPayload.getValue());

        assertThat(movementFromBd.getPaymentDate()).isNull();
        assertThat(movementFromBd.getState()).isEqualTo(Movement.State.OPEN);

        assertThat(movementFromBd.getContact().getExternalId()).isEqualTo(movementPayload.getContactId());
        assertThat(movementFromBd.getClassification().getExternalId()).isEqualTo(movementPayload.getClassificationId());
    }

    @Test
    void shouldSaveMovementWithoutContact() throws Exception {

        final var movementJson = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "")
                .replace("{classificationId}", "df9b84b3-5857-4cf9-be19-0936f7e5219c");

        final var movementPayload = performPostAndExpectCreated(BASE_URL, movementJson,
                MovementRegistrationPayload.class);

        assertThat(movementPayload).isNotNull();
        assertThat(movementPayload.getId()).isNotNull();

        final var optionalMovement = this.movementRepository.findByExternalId(movementPayload.getId());
        assertThat(optionalMovement).isPresent();

        final var movementFromBd = optionalMovement.get();
        assertThat(movementFromBd.getExternalId()).isEqualTo(movementPayload.getId());
        assertThat(movementFromBd.getDescription()).isEqualTo(movementPayload.getDescription());
        assertThat(movementFromBd.getDueDate()).isEqualTo(movementPayload.getDueDate());
        assertThat(movementFromBd.getValue()).isEqualTo(movementPayload.getValue());

        assertThat(movementFromBd.getPaymentDate()).isNull();
        assertThat(movementFromBd.getState()).isEqualTo(Movement.State.OPEN);

        assertThat(movementFromBd.getContact()).isNull();
        assertThat(movementFromBd.getClassification().getExternalId()).isEqualTo(movementPayload.getClassificationId());
    }

    @Test
    void shouldNotSaveAndFailWhenUnknownContact() throws Exception {

        final var movementJson = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "74dbfa26-8fdc-4287-ab94-b14420d0276d")
                .replace("{classificationId}", "df9b84b3-5857-4cf9-be19-0936f7e5219c");

        performPost(BASE_URL, movementJson)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotSaveAndFailWhenUnknownClassification() throws Exception {

        final var movementJson = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "")
                .replace("{classificationId}", "74dbfa26-8fdc-4287-ab94-b14420d0276d");

        performPost(BASE_URL, movementJson)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailOnEmptyRequiredFields() throws Exception {

    }

    @Test
    void shouldUpdateMovement() throws Exception {
        
    }

    @Test
    void shouldQueryMovementsWithPagination() throws Exception {
        
    }

    @Test
    void shouldFindAnMovementByExternalId() throws Exception {

    }

    @Test
    void shouldFindMovementUsingFilters() throws Exception {

    }

    @Test
    void shouldDeleteAnMovement() throws Exception {

    }
}
