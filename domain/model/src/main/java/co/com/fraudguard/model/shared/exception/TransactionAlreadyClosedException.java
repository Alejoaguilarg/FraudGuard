package co.com.fraudguard.model.shared.exception;

public class TransactionAlreadyClosedException extends InvalidDomainStateException {
    public TransactionAlreadyClosedException(String message) {
        super(message);
    }
}
