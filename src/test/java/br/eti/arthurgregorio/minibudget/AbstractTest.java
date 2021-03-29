package br.eti.arthurgregorio.minibudget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@Slf4j
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AbstractTest {

    @Autowired
    private ObjectMapper objectMapper;

    protected <T> T toObject(String json, Class<T> targetClazz) {
        try {
            return this.objectMapper.readValue(json, targetClazz);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to convert the given JSON to object", ex);
        }
    }

    protected <T> T toObject(String json, TypeReference<T> targetClazz) {
        try {
            return this.objectMapper.readValue(json, targetClazz);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to convert the given JSON to object", ex);
        }
    }

    protected <T> T toObject(String json, String node, TypeReference<T> targetClazz) {
        try {
            final var nodes = objectMapper.readTree(json);
            final var specificNode = nodes.at(node);
            return this.objectMapper.readValue(specificNode.toString(), targetClazz);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to convert the given JSON to object", ex);
        }
    }

    protected String resourceAsString(Resource resource) {
        try {
            return Resources.toString(resource.getURL(), Charsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load resource", ex);
        }
    }
}
