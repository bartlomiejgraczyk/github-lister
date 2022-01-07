package pl.bgraczyk.githublister.client.error;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kohsuke.github.GitHubRateLimitHandler;
import org.kohsuke.github.connector.GitHubConnectorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import pl.bgraczyk.githublister.client.error.exception.GitHubResponseClientException;

public class GitHubClientRateLimitHandler extends GitHubRateLimitHandler {

    public GitHubClientRateLimitHandler() {
        super();
    }

    @Override
    public void onError(@Nonnull GitHubConnectorResponse response) {
        HttpStatus status;

        try {
            status = HttpStatus.valueOf(response.statusCode());
        } catch (IllegalArgumentException e) {
            throw new UnknownHttpStatusCodeException(response.statusCode(),
                String.valueOf(response.statusCode()), getHttpHeaders(response),
                getResponseBody(response), getCharset(response));
        }

        throw getResponseError(response, status);
    }

    private HttpHeaders getHttpHeaders(GitHubConnectorResponse response) {
        MultiValueMap<String, String> httpHeadersMap = new LinkedMultiValueMap<>(response.allHeaders());
        return HttpHeaders.readOnlyHttpHeaders(httpHeadersMap);
    }

    private byte[] getResponseBody(GitHubConnectorResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.bodyStream());
        }
        catch (IOException ex) {
            return new byte[0];
        }
    }

    private Charset getCharset(GitHubConnectorResponse response) {
        Map<String, List<String>> headers = response.allHeaders();
        List<String> contentTypes = headers.get(HttpHeaders.CONTENT_TYPE);
        List<MediaType> mediaTypes = MediaType.parseMediaTypes(contentTypes);
        Optional<MediaType> mediaType = mediaTypes.stream().filter(Objects::nonNull).findFirst();
        return mediaType.isEmpty() ? null : mediaType.get().getCharset();
    }

    private GitHubResponseClientException getResponseError(GitHubConnectorResponse response, HttpStatus status) {
        String jsonString = new String(getResponseBody(response), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        String message;

        try {
            jsonNode = objectMapper.readTree(jsonString);
            message = objectMapper.writeValueAsString(jsonNode.get("message"));
        } catch (JsonProcessingException e) {
            return new GitHubResponseClientException(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new GitHubResponseClientException(message, status);
    }
}
