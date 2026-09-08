package co.com.fraudguard.model.transaction;

public record RiskScore(double value, RiskLevel level) {

    public RiskScore(double value) {
        this(value, null);
    }

    public RiskScore {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("Invalid risk score: " + value + ". Must be between 0 and 1");
        }
        level = deriveLevel(value);
    }

    public boolean isCritical(){
        return level == RiskLevel.CRITICAL;
    }

    private static RiskLevel deriveLevel(double value){
        if (value > 0.9) return RiskLevel.CRITICAL;
        if (value > 0.7) return RiskLevel.HIGH;
        if (value > 0.4) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }
}
