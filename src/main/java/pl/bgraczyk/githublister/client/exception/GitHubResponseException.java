package pl.bgraczyk.githublister.client.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.bgraczyk.githublister.exception.BaseAppException;

@Getter
public class GitHubResponseException extends BaseAppException {

    private HttpStatus code;

    public GitHubResponseException(String message) {
        super(message);
    }

    public GitHubResponseException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
