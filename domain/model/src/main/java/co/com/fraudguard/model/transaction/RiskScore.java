package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.enums.RiskLevel;

public record RiskScore(double value, RiskLevel level) {

    public RiskScore(double value) {
        this(value, deriveLevel(value));
    }

    public RiskScore {
        if (value < 0.0 || value > 1) {
            throw new IllegalArgumentException("Invalid risk score: " + value + ". Must be between 0 and 1");
        }
        if (level == null) {
            throw new IllegalArgumentException("Risk level must not be null");
        }
    }

    public boolean isCritical(){
        return value > 0.9;
    }

    private static RiskLevel deriveLevel(double value){
        if (value > 0.9) return RiskLevel.CRITICAL;
        if (value > 0.7) return RiskLevel.HIGH;
        if (value > 0.4) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }
}
