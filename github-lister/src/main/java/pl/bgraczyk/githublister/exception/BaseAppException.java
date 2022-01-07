package pl.bgraczyk.githublister.exception;

public abstract class BaseAppException extends RuntimeException {

    protected BaseAppException(final String message) {
        super(message);
    }
}
