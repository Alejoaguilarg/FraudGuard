package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.enums.RiskLevel;

public record RiskScore(double value, RiskLevel level) {

    public RiskScore {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Invalid risk score: " + value + ". Must be between 0 and 1");
        }
    }

    public boolean isCritical(){
        return value > 0.9;
    }


}
