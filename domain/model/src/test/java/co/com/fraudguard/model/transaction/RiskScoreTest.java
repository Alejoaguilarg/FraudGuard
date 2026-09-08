package co.com.fraudguard.model.transaction;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("0.9 exactly is NOT critical — threshold is strictly > 0.9")
    void mustNotBeCritical(){
        assertFalse(new RiskScore(0.85).isCritical());
        assertFalse(new RiskScore(0.9).isCritical());
    }

    @Test
    @DisplayName("Rejects inconsistent level")
    void mustRejectInconsistentLevel(){
        assertThrows(IllegalArgumentException.class, () -> new RiskScore(0.95, RiskLevel.LOW));
    }

    @Test
    @DisplayName("Accepts consistent level")
     void mustAcceptsConsistentLevel(){
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
}
