package br.eti.arthurgregorio.minibudget.controllers;

import br.eti.arthurgregorio.minibudget.AbstractControllerTest;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
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
    void shouldSaveContact() throws Exception {

        final var contact = performPostAndExpectCreated(BASE_URL,
                resourceAsString(this.newContactJson), Contact.class);

        assertThat(contact).isNotNull();
        assertThat(contact.getExternalId()).isNotNull();

        final var optionalContact = this.contactRepository.findByExternalId(contact.getExternalId());
        assertThat(optionalContact).isPresent();

        final var contactFromBd = optionalContact.get();
        assertThat(contactFromBd.getExternalId()).isEqualTo(contact.getExternalId());
        assertThat(contactFromBd.getName()).isEqualTo("Client #4");
        assertThat(contactFromBd.getEmail()).isEqualTo("client_four@email.com");
        assertThat(contactFromBd.getTelephone()).isNull();
    }

    @Test
    void shouldFailOnEmptyRequiredFields() throws Exception {

        final var payload = resourceAsString(this.newContactJson)
                .replace("Client #4", "")
                .replace("client_four@email.com", "");

        performPost(BASE_URL, payload, status().is(422)).andExpect(
                jsonPath("$.violations[*].property", containsInAnyOrder("name", "email"))
        );
    }

    @Test
    void shouldUpdateContact() throws Exception {

        final var contact = performPutAndExpectOk(BASE_URL + "/df9b84b3-5857-4cf9-be19-0936f7e5219c",
                resourceAsString(this.updateContactJson), Contact.class);

        assertThat(contact).isNotNull();
        assertThat(contact.getExternalId()).isNotNull();

        final var optionalContact = this.contactRepository.findByExternalId(contact.getExternalId());
        assertThat(optionalContact).isPresent();

        final var contactFromBd = optionalContact.get();
        assertThat(contactFromBd.getExternalId().toString()).isEqualTo("df9b84b3-5857-4cf9-be19-0936f7e5219c");
        assertThat(contactFromBd.getName()).isEqualTo("Client #5");
        assertThat(contactFromBd.getEmail()).isEqualTo("client_five@email.com");
        assertThat(contactFromBd.getTelephone()).isEqualTo("+55 48 1111-2222");
    }

    @Test
    void shouldQueryContactsWithPagination() throws Exception {

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
    void shouldFindAnContactByExternalId() throws Exception {

        final var contact = performGetAndExpectOk(
                BASE_URL + "/6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c", Contact.class);

        assertThat(contact).isNotNull();
        assertThat(contact.getExternalId().toString()).isEqualTo("6ce09b57-1fc6-4dcf-84d0-c923c7ed1a1c");
        assertThat(contact.getName()).isEqualTo("Client #3");
        assertThat(contact.getEmail()).isEqualTo("client_three@email.com");
        assertThat(contact.getTelephone()).isEqualTo("+55 45 1212-5677");
    }

    @Test
    void shouldFindContactUsingFilters() throws Exception {

        final var contacts = performGetPaginated(BASE_URL,
                Map.of("email", "client_two@email.com"), Contact.class);

        assertThat(contacts)
                .hasSize(1)
                .extracting("id", "name")
                .contains(tuple("8c7dd89c-1717-42ae-b9a0-89f3f690b07d", "Client #2"));
    }

    @Test
    void shouldDeleteAnContact() throws Exception {

        performDeleteAndExpectOk(BASE_URL + "/df9b84b3-5857-4cf9-be19-0936f7e5219c");

        final var optionalContact = this.contactRepository.findByExternalId(
                UUID.fromString("df9b84b3-5857-4cf9-be19-0936f7e5219c"));

        assertThat(optionalContact).isNotPresent();
    }
}
