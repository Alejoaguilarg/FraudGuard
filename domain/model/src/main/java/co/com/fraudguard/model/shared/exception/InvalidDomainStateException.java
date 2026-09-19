package co.com.fraudguard.model.shared.exception;

public abstract class InvalidDomainStateException extends DomainException {
    protected InvalidDomainStateException(String message) {
        super(message);
    }
}
