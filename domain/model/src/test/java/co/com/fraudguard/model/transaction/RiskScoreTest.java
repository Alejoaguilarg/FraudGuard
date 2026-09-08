package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.enums.RiskLevel;
import org.junit.jupiter.api.Test;

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
        assertTrue(new RiskScore(0.95).level() == RiskLevel.CRITICAL);
        assertTrue(new RiskScore(0.9).level() == RiskLevel.HIGH);
        assertTrue(new RiskScore(0.5).level() == RiskLevel.MEDIUM);
        assertTrue(new RiskScore(0.4).level() == RiskLevel.LOW);
    }
}
