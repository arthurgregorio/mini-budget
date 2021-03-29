package br.eti.arthurgregorio.minibudget.controllers;

import br.eti.arthurgregorio.minibudget.AbstractControllerTest;
import br.eti.arthurgregorio.minibudget.application.payloads.ContactPayload;
import br.eti.arthurgregorio.minibudget.model.repositories.ContactRepository;
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

public class ContactControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/contacts";

    @Value("classpath:/payloads/contact/create.json")
    private Resource newContactJson;
    @Value("classpath:/payloads/contact/update.json")
    private Resource updateContactJson;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void shouldSave() throws Exception {

        final var response = performPostAndExpectCreated(BASE_URL, resourceAsString(this.newContactJson),
                ContactPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.contactRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var contact = result.get();
        assertThat(contact.getExternalId()).isEqualTo(response.getId());
        assertThat(contact.getName()).isEqualTo("Client #4");
        assertThat(contact.getEmail()).isEqualTo("client_four@email.com");
        assertThat(contact.getTelephone()).isNull();
    }

    @Test
    void shouldFailOnEmptyRequiredFields() throws Exception {

        final var payload = resourceAsString(this.newContactJson)
                .replace("Client #4", "")
                .replace("client_four@email.com", "");

        performPost(BASE_URL, payload, status().is(422))
                .andExpect(jsonPath("$.violations[*].property", containsInAnyOrder("name", "email")));
    }

    @Test
    void shouldUpdate() throws Exception {

        final var response = performPutAndExpectOk(BASE_URL + "/8c7dd89c-1717-42ae-b9a0-89f3f690b07d",
                resourceAsString(this.updateContactJson), ContactPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        final var result = this.contactRepository.findByExternalId(response.getId());
        assertThat(result).isPresent();

        final var contact = result.get();
        assertThat(contact.getExternalId().toString()).isEqualTo("8c7dd89c-1717-42ae-b9a0-89f3f690b07d");
        assertThat(contact.getName()).isEqualTo("Client #5");
        assertThat(contact.getEmail()).isEqualTo("client_five@email.com");
        assertThat(contact.getTelephone()).isEqualTo("+55 48 1111-2222");
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
                BASE_URL + "/6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", ContactPayload.class);

        assertThat(response).isNotNull();
        assertThat(response.getId().toString()).isEqualTo("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c");
        assertThat(response.getName()).isEqualTo("Client #3");
        assertThat(response.getEmail()).isEqualTo("client_three@email.com");
        assertThat(response.getTelephone()).isEqualTo("+55 45 1212-5677");
    }

    @Test
    void shouldFindUsingFilters() throws Exception {

        final var response = performGetPaginated(BASE_URL,
                Map.of("email", "client_three@email.com"), ContactPayload.class);

        assertThat(response)
                .hasSize(1)
                .extracting("id", "name")
                .contains(tuple("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", "Client #3"));
    }

    @Test
    void shouldDelete() throws Exception {

        performDeleteAndExpectOk(BASE_URL + "/df9b84b3-5857-4cf9-be19-0936f7e5219c");

        final var result = this.contactRepository.findByExternalId(
                UUID.fromString("df9b84b3-5857-4cf9-be19-0936f7e5219c"));

        assertThat(result).isNotPresent();
    }

    @Test
    void shouldFailWhenHasLinkWithMovement() throws Exception {

        performDeleteAndExpect(BASE_URL + "/607ef966-f6c0-48b7-bbb1-ec2a36191509", status().isBadRequest());

        final var result = this.contactRepository.findByExternalId(
                UUID.fromString("607ef966-f6c0-48b7-bbb1-ec2a36191509"));

        assertThat(result).isPresent();
    }
}
