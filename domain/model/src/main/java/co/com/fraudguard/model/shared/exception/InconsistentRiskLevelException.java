package co.com.fraudguard.model.shared.exception;

public class InconsistentRiskLevelException extends InvalidDomainDataException {
    public InconsistentRiskLevelException(String message) {
        super(message);
    }
}
