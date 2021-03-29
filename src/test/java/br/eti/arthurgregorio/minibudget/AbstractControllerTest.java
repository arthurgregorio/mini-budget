package br.eti.arthurgregorio.minibudget;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AbstractControllerTest extends AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    protected <T> List<T> performGetPaginated(String urlTemplate, Class<T> targetResponse) throws Exception {
        final var result = performGet(urlTemplate, Map.of(), status().isPartialContent()).andReturn();
        return this.getPaginatedResult(result.getResponse(), targetResponse);
    }

    protected <T> List<T> performGetPaginated(String urlTemplate, Map<String, String> parameters, Class<T> targetResponse) throws Exception {
        final var result = performGet(urlTemplate, parameters, status().isPartialContent()).andReturn();
        return this.getPaginatedResult(result.getResponse(), targetResponse);
    }

    protected <T> T performGetAndExpectOk(String urlTemplate, Class<T> targetResponse) throws Exception {
        final var result = performGet(urlTemplate, Map.of(), status().isOk()).andReturn();
        return this.toObject(result.getResponse().getContentAsString(), targetResponse);
    }

    protected ResultActions performGet(String urlTemplate, Map<String, String> parameters, ResultMatcher... resultMatchers) throws Exception {

        final var formParameters = this.covertToMultiValueMap(parameters);

        final ResultActions resultActions = this.mockMvc.perform(
                get(urlTemplate)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .params(formParameters));

        this.applyMatchers(resultActions, resultMatchers);

        return resultActions;
    }

    protected <T> T performPostAndExpectCreated(String urlTemplate, String jsonContent, Class<T> targetResponse) throws Exception {
        var result = performPost(urlTemplate, jsonContent, status().isCreated()).andReturn();
        return this.toObject(result.getResponse().getContentAsString(), targetResponse);
    }

    protected ResultActions performPost(String urlTemplate, String jsonContent, ResultMatcher... resultMatchers) throws Exception {

        final var resultActions = this.mockMvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent));

        this.applyMatchers(resultActions, resultMatchers);

        return resultActions;
    }

    protected <T> T performPutAndExpectOk(String urlTemplate, String jsonContent, Class<T> targetResponse) throws Exception {
        var result = performPut(urlTemplate, jsonContent, status().isOk());
        return toObject(result.getResponse().getContentAsString(), targetResponse);
    }

    protected MvcResult performPut(String urlTemplate, String jsonContent, ResultMatcher... resultMatchers) throws Exception {

        final var resultActions = this.mockMvc.perform(
                put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent));

        this.applyMatchers(resultActions, resultMatchers);

        return resultActions.andReturn();
    }

    protected void performDeleteAndExpect(String urlTemplate, ResultMatcher resultMatcher) throws Exception {
        this.mockMvc.perform(delete(urlTemplate)).andExpect(resultMatcher);
    }

    protected void performDeleteAndExpectOk(String urlTemplate) throws Exception {
        this.mockMvc.perform(delete(urlTemplate)).andExpect(status().isOk());
    }

    private MultiValueMap<String, String> covertToMultiValueMap(Map<String, String> parameters) {

        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        parameters.forEach((key, value) -> multiValueMap.put(key, List.of(value)));

        return multiValueMap;
    }

    private <T> List<T> getPaginatedResult(MockHttpServletResponse response, Class<T> target) throws Exception {
        return toObject(response.getContentAsString(), "/content", new TypeReference<List<T>>() {});
    }

    private void applyMatchers(ResultActions resultActions, ResultMatcher[] resultMatchers) throws Exception {
        for (ResultMatcher resultMatcher : resultMatchers) {
            resultActions.andExpect(resultMatcher);
        }
    }
}
