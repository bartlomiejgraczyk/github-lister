package pl.bgraczyk.githublister.client.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.bgraczyk.githublister.exception.BaseAppException;

@Getter
public class GitHubResponseClientException extends BaseAppException {

    private HttpStatus code;

    public GitHubResponseClientException(String message) {
        super(message);
    }

    public GitHubResponseClientException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
