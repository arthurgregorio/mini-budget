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
    void shouldSaveClassification() throws Exception {

        final var classification = performPostAndExpectCreated(BASE_URL,
                resourceAsString(this.newClassificationJson), ClassificationPayload.class);

        assertThat(classification).isNotNull();
        assertThat(classification.getId()).isNotNull();

        final var optionalClassification = this.classificationRepository.findByExternalId(classification.getId());
        assertThat(optionalClassification).isPresent();

        final var classificationFromBd = optionalClassification.get();
        assertThat(classificationFromBd.getExternalId()).isEqualTo(classification.getId());
        assertThat(classificationFromBd.getName()).isEqualTo("Rent");
        assertThat(classificationFromBd.getType()).isEqualTo(Classification.Type.EXPENSE);
    }

    @Test
    void shouldFailOnEmptyRequiredFields() throws Exception {

        final var payload = resourceAsString(this.newClassificationJson)
                .replace("Rent", "")
                .replace("EXPENSE", "");

        performPost(BASE_URL, payload, status().is(422)).andExpect(
                jsonPath("$.violations[*].property", containsInAnyOrder("name", "type"))
        );
    }

    @Test
    void shouldUpdateClassification() throws Exception {

        final var classification = performPutAndExpectOk(BASE_URL + "/df9b84b3-5857-4cf9-be19-0936f7e5219c",
                resourceAsString(this.updateClassificationJson), ClassificationPayload.class);

        assertThat(classification).isNotNull();
        assertThat(classification.getId()).isNotNull();

        final var optionalClassification = this.classificationRepository.findByExternalId(classification.getId());
        assertThat(optionalClassification).isPresent();

        final var classificationFromBd = optionalClassification.get();
        assertThat(classificationFromBd.getExternalId().toString()).isEqualTo("df9b84b3-5857-4cf9-be19-0936f7e5219c");
        assertThat(classificationFromBd.getName()).isEqualTo("Selling Things");
        assertThat(classificationFromBd.getType()).isEqualTo(Classification.Type.INCOME);
    }

    @Test
    void shouldQueryClassificationsWithPagination() throws Exception {

        final var result = performGet(
                BASE_URL,
                Map.of("page", "0", "size", "2"),
                status().isPartialContent());

        result.andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.empty", is(false)));
    }

    @Test
    void shouldFindAnClassificationByExternalId() throws Exception {

        final var classification = performGetAndExpectOk(
                BASE_URL + "/6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", ClassificationPayload.class);

        assertThat(classification).isNotNull();
        assertThat(classification.getId().toString()).isEqualTo("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c");
        assertThat(classification.getName()).isEqualTo("Freelancers");
        assertThat(classification.getType()).isEqualTo(Classification.Type.INCOME);
    }

    @Test
    void shouldFindClassificationUsingFilters() throws Exception {

        final var classifications = performGetPaginated(BASE_URL,
                Map.of("name", "Freelancers"), ClassificationPayload.class);

        assertThat(classifications)
                .hasSize(1)
                .extracting("id", "name")
                .contains(tuple("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", "Freelancers"));
    }

    @Test
    void shouldDeleteAnClassification() throws Exception {

        performDeleteAndExpectOk(BASE_URL + "/8c7dd89c-1717-42ae-b9a0-89f3f690b07d");

        final var optionalClassification = this.classificationRepository.findByExternalId(
                UUID.fromString("8c7dd89c-1717-42ae-b9a0-89f3f690b07d"));

        assertThat(optionalClassification).isNotPresent();
    }
}
