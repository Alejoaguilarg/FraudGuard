package co.com.fraudguard.model.shared.exception;

public class TransactionAlreadyEvaluatedException extends InvalidDomainStateException {
    public TransactionAlreadyEvaluatedException(String message) {
        super(message);
    }
}