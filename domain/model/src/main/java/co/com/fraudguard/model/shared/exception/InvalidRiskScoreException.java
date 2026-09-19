package co.com.fraudguard.model.shared.exception;

public class InvalidRiskScoreException extends InvalidDomainDataException {
    public InvalidRiskScoreException(String message) {
        super(message);
    }
}
