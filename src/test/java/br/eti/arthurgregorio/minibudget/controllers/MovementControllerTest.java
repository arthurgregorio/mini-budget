package br.eti.arthurgregorio.minibudget.controllers;

import br.eti.arthurgregorio.minibudget.AbstractControllerTest;
import br.eti.arthurgregorio.minibudget.application.payloads.MovementPayload;
import br.eti.arthurgregorio.minibudget.model.repositories.MovementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        final var movementPayload = performPostAndExpectCreated(BASE_URL,
                resourceAsString(this.newMovementJson), MovementPayload.class);

        assertThat(movementPayload).isNotNull();
        assertThat(movementPayload.getId()).isNotNull();

        final var optionalMovement = this.movementRepository.findByExternalId(movementPayload.getId());
        assertThat(optionalMovement).isPresent();

        final var movementFromBd = optionalMovement.get();
        assertThat(movementFromBd.getExternalId()).isEqualTo(movementPayload.getId());
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
