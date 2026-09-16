package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.transaction.TransactionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionMarkedSuspiciousTest {

    @Test
    @DisplayName("Must throw exception when transactionId is null")
    void mustThrowExceptionWhenTransactionIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionMarkedSuspicious(null, "reason", Instant.now()));
    }

    @Test
    @DisplayName("Must throw exception when reason is null")
    void mustThrowExceptionWhenReasonIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionMarkedSuspicious(TransactionId.generate(), null, Instant.now()));
    }

    @Test
    @DisplayName("Must throw exception when evaluatedAt is null")
    void mustThrowExceptionWhenTimestampIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionMarkedSuspicious(TransactionId.generate(), "reason", null));
    }

    @Test
    @DisplayName("occurredAt returns evaluatedAt")
    void occurredAtReturnsEvaluatedAt() {
        Instant now = Instant.now();
        TransactionMarkedSuspicious event = new TransactionMarkedSuspicious(
                TransactionId.generate(), "high amount", now);
        assertEquals(now, event.occurredAt());
    }
}
