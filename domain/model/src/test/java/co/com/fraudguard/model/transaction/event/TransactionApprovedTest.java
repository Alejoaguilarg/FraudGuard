package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.shared.exception.InvalidDomainEventException;
import co.com.fraudguard.model.transaction.Money;
import co.com.fraudguard.model.transaction.TransactionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionApprovedTest {

    @Test
    @DisplayName("must throw exception when transaction id is null")
    void mustThrowExceptionWhenTransactionIdIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionApproved(null, Money.of("100", "COP"), Instant.now()));
    }

    @Test
    @DisplayName("must throw exception when amount is null")
    void mustThrowExceptionWhenAmountIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionApproved(TransactionId.generate(), null, Instant.now()));
    }

    @Test
    @DisplayName("must throw exception when approved at is null")
    void mustThrowExceptionWhenApprovedAtIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionApproved(TransactionId.generate(), Money.of("100", "COP"), null));
    }

    @Test
    @DisplayName("must return approved at when occurred at is called")
    void occurredAtReturnsApprovedAt() {
        Instant now = Instant.now();
        TransactionApproved event = new TransactionApproved(TransactionId.generate(), Money.of("100", "COP"), now);
        assertEquals(now, event.occurredAt());
    }
}
