package co.com.fraudguard.model.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RiskScoreTest {

    @Test
    void mustBeBetween0And1() {
        assertThrows(IllegalArgumentException.class, () -> new RiskScore(-1));
        assertThrows(IllegalArgumentException.class, () -> new RiskScore(2));
    }

    @Test
    void mustBeCritical(){
        assertTrue(new RiskScore(0.95).isCritical());
        assertTrue(new RiskScore(0.94).isCritical());
    }

    @Test
    void mustNotBeCritical(){
        assertFalse(new RiskScore(0.85).isCritical());
        assertFalse(new RiskScore(0.9).isCritical());
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
    void canonicalConstructorDerivesLevel() {
        RiskScore score = new RiskScore(0.05, RiskLevel.CRITICAL);
        assertEquals(RiskLevel.LOW, score.level());
    }
}
