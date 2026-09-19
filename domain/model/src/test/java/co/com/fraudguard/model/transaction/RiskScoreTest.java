package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.InconsistentRiskLevelException;
import co.com.fraudguard.model.shared.exception.InvalidRiskScoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RiskScoreTest {

    @Test
    void mustBeBetween0And1() {
        assertThrows(InvalidRiskScoreException.class, () -> new RiskScore(-1));
        assertThrows(InvalidRiskScoreException.class, () -> new RiskScore(2));
    }

    @Test
    void mustBeCritical(){
        assertTrue(new RiskScore(0.95).isCritical());
        assertTrue(new RiskScore(0.91).isCritical());  // justo por encima del umbral
        assertTrue(new RiskScore(1.0).isCritical());   // límite superior
    }

    @Test
    @DisplayName("0.9 exactly is NOT critical — threshold is strictly > 0.9")
    void mustNotBeCritical(){
        assertFalse(new RiskScore(0.85).isCritical());
        assertFalse(new RiskScore(0.9).isCritical());
    }

    @Test
    @DisplayName("Rejects inconsistent level")
    void mustRejectInconsistentLevel(){
        assertThrows(InconsistentRiskLevelException.class, () -> new RiskScore(0.95, RiskLevel.LOW));
        assertThrows(InconsistentRiskLevelException.class, () -> new RiskScore(0.9, RiskLevel.CRITICAL));
    }

    @Test
    @DisplayName("Accepts consistent level")
    void mustAcceptConsistentLevel(){
        RiskScore score = new RiskScore(0.95, RiskLevel.CRITICAL);
        assertEquals(RiskLevel.CRITICAL, score.level());
    }

    @Test
    void mustDeriveCorrectLevel(){
        assertEquals(RiskLevel.CRITICAL, new RiskScore(0.95).level());
        assertEquals(RiskLevel.HIGH, new RiskScore(0.9).level());
        assertEquals(RiskLevel.MEDIUM, new RiskScore(0.5).level());
        assertEquals(RiskLevel.LOW, new RiskScore(0.4).level());
        assertEquals(RiskLevel.LOW, new RiskScore(0.05).level());
    }

    @Test
    @DisplayName("accepts boundary values 0.0 and 1.0")
    void mustAcceptBoundaries() {
        assertDoesNotThrow(() -> new RiskScore(0.0));
        assertDoesNotThrow(() -> new RiskScore(1.0));
    }
}
