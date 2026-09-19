package co.com.fraudguard.model.shared.exception;

public class InvalidTransactionIdException extends InvalidDomainDataException {
    public InvalidTransactionIdException(String message) {
        super(message);
    }
}
