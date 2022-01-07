package pl.bgraczyk.githublister.client.error;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import pl.bgraczyk.githublister.client.error.exception.GitHubResponseClientException;

public class GitHubResponseErrorHandler extends DefaultResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus status;

        try {
            status = HttpStatus.valueOf(response.getRawStatusCode());
        } catch (IllegalArgumentException e) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(), response.getStatusText(),
                response.getHeaders(), getResponseBody(response), getCharset(response));
        }

        throw getResponseError(response, status);
    }

    private GitHubResponseClientException getResponseError(ClientHttpResponse response, HttpStatus status) throws JsonProcessingException {
        String jsonString = new String(getResponseBody(response), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            return new GitHubResponseClientException(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String message = objectMapper.writeValueAsString(jsonNode.get("message"));
        return new GitHubResponseClientException(message, status);
    }
}
