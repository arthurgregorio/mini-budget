package br.eti.arthurgregorio.minibudget.controllers;

import br.eti.arthurgregorio.minibudget.AbstractControllerTest;
import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationPayload;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import br.eti.arthurgregorio.minibudget.model.repositories.ClassificationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClassificationControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/classifications";

    @Value("classpath:/payloads/classification/create.json")
    private Resource newClassificationJson;
    @Value("classpath:/payloads/classification/update.json")
    private Resource updateClassificationJson;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Test
    void shouldSave() throws Exception {

        final var response = performPostAndExpectCreated(BASE_URL,
                resourceAsString(this.newClassificationJson), ClassificationPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.classificationRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var classification = result.get();
        assertThat(classification.getExternalId()).isEqualTo(response.getId());
        assertThat(classification.getName()).isEqualTo("Rent");
        assertThat(classification.getType()).isEqualTo(Classification.Type.EXPENSE);
    }

    @Test
    void shouldFailOnEmptyRequiredFields() throws Exception {

        final var payload = resourceAsString(this.newClassificationJson)
                .replace("Rent", "")
                .replace("EXPENSE", "");

        performPost(BASE_URL, payload, status().is(422))
                .andExpect(jsonPath("$.violations[*].property", containsInAnyOrder("name", "type")));
    }

    @Test
    void shouldUpdate() throws Exception {

        final var response = performPutAndExpectOk(BASE_URL + "/df9b84b3-5857-4cf9-be19-0936f7e5219c",
                resourceAsString(this.updateClassificationJson), ClassificationPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.classificationRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var classification = result.get();
        assertThat(classification.getExternalId().toString()).isEqualTo("df9b84b3-5857-4cf9-be19-0936f7e5219c");
        assertThat(classification.getName()).isEqualTo("Selling Things");
        assertThat(classification.getType()).isEqualTo(Classification.Type.INCOME);
    }

    @Test
    void shouldQueryWithPagination() throws Exception {

        final var response = performGet(
                BASE_URL,
                Map.of("page", "0", "size", "2"),
                status().isPartialContent());

        response.andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.empty", is(false)));
    }

    @Test
    void shouldFindByExternalId() throws Exception {

        final var response = performGetAndExpectOk(
                BASE_URL + "/6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", ClassificationPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId().toString()).isEqualTo("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c");
        assertThat(response.getName()).isEqualTo("Freelancers");
        assertThat(response.getType()).isEqualTo(Classification.Type.INCOME);
    }

    @Test
    void shouldFindUsingFilters() throws Exception {

        final var response = performGetPaginated(BASE_URL,
                Map.of("name", "Freelancers"), ClassificationPayload.class);

        assertThat(response)
                .hasSize(1)
                .extracting("id", "name")
                .contains(tuple("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", "Freelancers"));
    }

    @Test
    void shouldDelete() throws Exception {

        performDeleteAndExpectOk(BASE_URL + "/8c7dd89c-1717-42ae-b9a0-89f3f690b07d");

        final var result = this.classificationRepository.findByExternalId(
                UUID.fromString("8c7dd89c-1717-42ae-b9a0-89f3f690b07d"));

        assertThat(result).isNotPresent();
    }

    @Test
    void shouldFailWhenHasLinkWithMovement() throws Exception {

        performDeleteAndExpect(BASE_URL + "/6bd6f187-a67c-4a70-be47-ca6b18489bbc", status().isBadRequest());

        final var result = this.classificationRepository.findByExternalId(
                UUID.fromString("6bd6f187-a67c-4a70-be47-ca6b18489bbc"));

        assertThat(result).isPresent();
    }
}
