package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.transaction.TransactionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionBlockedTest {

    @Test
    @DisplayName("Must throw exception when occurredAt is null")
    void mustThrowExceptionWhenOccurredAtIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionBlocked(null, "reason", Instant.now()));
    }

    @Test
    @DisplayName("Must throw exception when reason is null")
    void mustThrowExceptionWhenReasonIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionBlocked(TransactionId.generate(), null, Instant.now()));
    }

    @Test
    @DisplayName("Must throw exception when reason is empty")
    void mustThrowExceptionWhenReasonIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionBlocked(TransactionId.generate(), " ", Instant.now()));
    }

    @Test
    @DisplayName("occurredAt returns the timestamp passed at construction")
    void occurredAtReturnsTimestamp() {
        Instant now = Instant.now();
        TransactionBlocked event = new TransactionBlocked(TransactionId.generate(), "sanctioned", now);
        assertEquals(now, event.occurredAt());
    }
}
