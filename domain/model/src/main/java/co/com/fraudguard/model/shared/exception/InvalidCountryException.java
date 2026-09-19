package co.com.fraudguard.model.shared.exception;

public class InvalidCountryException extends InvalidDomainDataException {
    public InvalidCountryException(String message) {
        super(message);
    }
}
