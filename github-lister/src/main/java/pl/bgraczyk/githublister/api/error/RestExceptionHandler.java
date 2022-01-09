package pl.bgraczyk.githublister.api.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.bgraczyk.githublister.client.error.exception.GitHubResponseClientException;

@ControllerAdvice(basePackages = "pl.bgraczyk.githublister.api")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GitHubResponseClientException.class})
    public ResponseEntity<Object> handleGitHubResponseClientException(Exception e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
