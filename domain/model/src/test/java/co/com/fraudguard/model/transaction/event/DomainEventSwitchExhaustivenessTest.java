package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.transaction.Money;
import co.com.fraudguard.model.transaction.TransactionId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainEventSwitchExhaustivenessTest {

    private String describe(DomainEvent event) {
        return switch (event) {
            case TransactionCreated c -> "created:" + c.transactionId().value();
            case TransactionMarkedSuspicious s -> "suspicious:" + s.reason();
            case TransactionBlocked b -> "blocked:" + b.reason();
            case TransactionApproved a -> "approved:" + a.transactionId().value();
        };
    }

    @Test
    void switchIsExhaustiveOverAllEventTypes() {
        Instant now = Instant.now();
        TransactionId id = TransactionId.generate();
        Money amount = Money.of("100", "COP");

        assertEquals("created:" + id.value(),
                describe(new TransactionCreated(id, amount, "COL", now)));
        assertEquals("suspicious:high amount",
                describe(new TransactionMarkedSuspicious(id, "high amount", now)));
        assertEquals("blocked:sanctioned country",
                describe(new TransactionBlocked(id, "sanctioned country", now)));
        assertEquals("approved:" + id.value(),
                describe(new TransactionApproved(id, amount, now)));
    }
}