package co.com.fraudguard.model.shared.exception;

public class CurrencyMismatchException extends InvalidDomainDataException {
    public CurrencyMismatchException(String message) {
        super(message);
    }
}
