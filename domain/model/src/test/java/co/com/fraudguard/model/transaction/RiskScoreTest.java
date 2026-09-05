package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.enums.RiskLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RiskScoreTest {

    @Test
    void mustBeBetween0And1() {
        assertThrows(IllegalArgumentException.class, () -> new RiskScore(-1, RiskLevel.HIGH));
        assertThrows(IllegalArgumentException.class, () -> new RiskScore(2, RiskLevel.HIGH));
    }

    @Test
    void mustBeCritical(){
        assertTrue(new RiskScore(0.95, RiskLevel.CRITICAL).isCritical());
    }

    @Test
    void mustNotBeCritical(){
        assertTrue(new RiskScore(0.94, RiskLevel.HIGH).isCritical());
        assertFalse(new RiskScore(0.85, RiskLevel.HIGH).isCritical());
    }

    @Test
    void mustNotBeCriticalIfIs09(){
        assertFalse(new RiskScore(0.9, RiskLevel.HIGH).isCritical());
    }
}
