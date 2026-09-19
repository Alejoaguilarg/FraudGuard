package co.com.fraudguard.model.shared.exception;

public abstract class InvalidDomainDataException extends DomainException {
    protected InvalidDomainDataException(String message) {
        super(message);
    }
}
