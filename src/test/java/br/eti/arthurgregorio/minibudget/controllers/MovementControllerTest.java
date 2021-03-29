package br.eti.arthurgregorio.minibudget.controllers;

import br.eti.arthurgregorio.minibudget.AbstractControllerTest;
import br.eti.arthurgregorio.minibudget.application.payloads.MovementRegistrationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.repositories.MovementRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
    void shouldSave() throws Exception {

        final var payload = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "607ef966-f6c0-48b7-bbb1-ec2a36191509")
                .replace("{classificationId}", "6bd6f187-a67c-4a70-be47-ca6b18489bbc");

        final var response = performPostAndExpectCreated(BASE_URL, payload,
                MovementRegistrationPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.movementRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var movement = result.get();
        assertThat(movement.getExternalId()).isEqualTo(response.getId());
        assertThat(movement.getDescription()).isEqualTo("Test movement");
        assertThat(movement.getDueDate()).isEqualTo(LocalDate.of(2021, 10, 20));
        assertThat(movement.getValue()).isEqualTo(new BigDecimal(150));

        assertThat(movement.getPaymentDate()).isNull();
        assertThat(movement.getState()).isEqualTo(Movement.State.OPEN);

        assertThat(movement.getContact().getExternalId())
                .isEqualTo(UUID.fromString("607ef966-f6c0-48b7-bbb1-ec2a36191509"));
        assertThat(movement.getClassification().getExternalId())
                .isEqualTo(UUID.fromString("6bd6f187-a67c-4a70-be47-ca6b18489bbc"));
    }

    @Test
    void shouldSaveWithoutContact() throws Exception {

        final var payload = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "")
                .replace("{classificationId}", "6bd6f187-a67c-4a70-be47-ca6b18489bbc");

        final var response = performPostAndExpectCreated(BASE_URL, payload,
                MovementRegistrationPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.movementRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var movement = result.get();
        assertThat(movement.getExternalId()).isEqualTo(response.getId());
        assertThat(movement.getDescription()).isEqualTo(response.getDescription());
        assertThat(movement.getDueDate()).isEqualTo(response.getDueDate());
        assertThat(movement.getValue()).isEqualTo(response.getValue());

        assertThat(movement.getPaymentDate()).isNull();
        assertThat(movement.getState()).isEqualTo(Movement.State.OPEN);

        assertThat(movement.getContact()).isNull();
        assertThat(movement.getClassification().getExternalId()).isEqualTo(response.getClassificationId());
    }

    @Test
    void shouldNotSaveAndFailWhenUnknownContact() throws Exception {

        final var payload = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "74dbfa26-8fdc-4287-ab94-b14420d0276d")
                .replace("{classificationId}", "6bd6f187-a67c-4a70-be47-ca6b18489bbc");

        performPost(BASE_URL, payload)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldNotSaveAndFailWhenUnknownClassification() throws Exception {

        final var payload = resourceAsString(this.newMovementJson)
                .replace("{contactId}", "")
                .replace("{classificationId}", "74dbfa26-8fdc-4287-ab94-b14420d0276d");

        performPost(BASE_URL, payload)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldFailOnEmptyRequiredFields() throws Exception {

        final var requiredFields = new String[]{"description", "dueDate", "value", "classificationId"};

        performPost(BASE_URL, "{}", status().is(422))
                .andExpect(jsonPath("$.violations[*].property", containsInAnyOrder(requiredFields)));
    }

    @Test
    void shouldNotAcceptLessThanZeroAsMovementValue() throws Exception {

        final var payload = resourceAsString(this.newMovementJson)
                .replace("150", "-1")
                .replace("{contactId}", "")
                .replace("{classificationId}", "6bd6f187-a67c-4a70-be47-ca6b18489bbc");

        performPost(BASE_URL, payload, status().is(422))
                .andExpect(jsonPath("$.violations[*].property", contains("value")))
                .andExpect(jsonPath("$.violations[*].message", contains("movement.value.is-less-than-zero")));
    }

    @Test
    void shouldUpdateMovement() throws Exception {

        final var payload = resourceAsString(this.updateMovementJson)
                .replace("{contactId}", "")
                .replace("{classificationId}", "6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c");

        final var response = performPutAndExpectOk(BASE_URL
                + "/49b440cd-ef00-42d1-af24-ec217498f9ca", payload, MovementRegistrationPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.movementRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var movement = result.get();
        assertThat(movement.getExternalId())
                .isEqualTo(UUID.fromString("49b440cd-ef00-42d1-af24-ec217498f9ca"));

        assertThat(movement.getDescription()).isEqualTo("Another movement");
        assertThat(movement.getDueDate()).isEqualTo(LocalDate.of(2021, 6, 15));
        assertThat(movement.getValue()).isEqualTo(new BigDecimal(1));

        assertThat(movement.getPaymentDate()).isNull();
        assertThat(movement.getState()).isEqualTo(Movement.State.OPEN);

        assertThat(movement.getContact()).isNull();
        assertThat(movement.getClassification().getExternalId())
                .isEqualTo(UUID.fromString("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c"));
    }

    @Test
    @Disabled
    void shouldQueryWithPagination() throws Exception {
    }

    @Test
    void shouldFindByExternalId() throws Exception {

        final var response = performGetAndExpectOk(
                BASE_URL + "/01a81ac4-2cb6-4efc-847c-3aa550d6586a", MovementRegistrationPayload.class);

        assertThat(response.getId()).isEqualTo(UUID.fromString("01a81ac4-2cb6-4efc-847c-3aa550d6586a"));

        assertThat(response.getDescription()).isEqualTo("Movement to search");
        assertThat(response.getDueDate()).isEqualTo(LocalDate.of(2021, 11, 17));
        assertThat(response.getValue()).isEqualTo(new BigDecimal(100));

        assertThat(response.getContactId()).isNull();
        assertThat(response.getClassificationId()).isEqualTo(UUID.fromString("6bd6f187-a67c-4a70-be47-ca6b18489bbc"));
    }

    @Test
    @Disabled
    void shouldFindUsingFilters() throws Exception {
    }

    @Test
    void shouldDelete() throws Exception {

        final var uuid = UUID.fromString("61e19e7b-9655-4dc5-8657-0377054ac0dc");

        performDeleteAndExpectOk(BASE_URL + "/" + uuid.toString());

        final var result = this.movementRepository.findByExternalId(uuid);
        assertThat(result).isNotPresent();
    }
}
