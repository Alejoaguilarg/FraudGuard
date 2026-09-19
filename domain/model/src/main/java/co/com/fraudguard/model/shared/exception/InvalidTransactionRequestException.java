package co.com.fraudguard.model.shared.exception;

public class InvalidTransactionRequestException extends InvalidDomainDataException {
    public InvalidTransactionRequestException(String message) {
        super(message);
    }
}
