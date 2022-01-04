package pl.bgraczyk.githublister.client.abstraction;

import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public abstract class BaseRestClient {

    protected final RestTemplate restTemplate;

    protected BaseRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected void errorHandler(ResponseErrorHandler errorHandler) {
        this.restTemplate.setErrorHandler(errorHandler);
    }

    protected void defaultUri(String defaultUri) {
        this.restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(defaultUri));
    }
}
