package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.InconsistentRiskLevelException;
import co.com.fraudguard.model.shared.exception.InvalidRiskScoreException;

// RiskScore: Puntaje de riesgo de transacción, entre 0 y 1.
// Válida: Value entre 0.0 y 1.0 → InvalidRiskScoreException.
//         level no null y válido → InconsistentRiskLevelException.
public record RiskScore(double value, RiskLevel level) {

    public RiskScore(double value) {
        this(value, null);
    }

    public RiskScore {
        if (value < 0.0 || value > 1.0) {
            throw new InvalidRiskScoreException("Invalid risk score: " + value + ". Must be between 0 and 1");
        }
        RiskLevel derived = deriveLevel(value);
        if (level != null && level != derived) {
            throw new InconsistentRiskLevelException(
                    "Level " + level + " is inconsistent with value " + value + " (expected " + derived + ")"
            );
        }
        level = derived;
    }

    public boolean isCritical(){
        return level == RiskLevel.CRITICAL;
    }

    private static RiskLevel deriveLevel(double value){
        // Umbrales estrictos (>): el valor exacto del borde pertenece al nivel inferior.
        // Decisión de negocio — revisar si negocio requiere >= en el futuro.
        if (value > 0.9) return RiskLevel.CRITICAL;
        if (value > 0.7) return RiskLevel.HIGH;
        if (value > 0.4) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }
}
