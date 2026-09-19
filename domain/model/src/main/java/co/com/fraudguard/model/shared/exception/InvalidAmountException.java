package co.com.fraudguard.model.shared.exception;

public class InvalidAmountException extends InvalidDomainDataException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
