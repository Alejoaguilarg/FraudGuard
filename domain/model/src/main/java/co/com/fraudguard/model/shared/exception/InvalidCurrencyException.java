package co.com.fraudguard.model.shared.exception;

public class InvalidCurrencyException extends InvalidDomainDataException {
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
