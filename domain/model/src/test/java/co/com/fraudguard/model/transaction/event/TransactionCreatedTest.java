package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.shared.exception.InvalidDomainEventException;
import co.com.fraudguard.model.transaction.Money;
import co.com.fraudguard.model.transaction.TransactionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionCreatedTest {

    @Test
    @DisplayName("must throw exception when amount is null")
    void mustThrowExceptionWhenAmountIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionCreated(TransactionId.generate(), null, "COL", Instant.now()));
    }

    @Test
    @DisplayName("must throw exception when transactionId is null")
    void mustThrowExceptionWhenTransactionIdIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionCreated(null, Money.of("100", "COP"), "COL", Instant.now()));
    }

    @Test
    @DisplayName("must throw exception when createdAt is null")
    void mustThrowExceptionWhenInstantIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionCreated(TransactionId.generate(), Money.of("100", "COP"), "COL", null));
    }

    @Test
    @DisplayName("must throw exception when country is null")
    void mustThrowExceptionWhenCountryIsNull() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionCreated(TransactionId.generate(), Money.of("100", "COP"), null, Instant.now()));
    }

    @Test
    @DisplayName("must throw exception when country is blank")
    void mustThrowExceptionWhenCountryIsBlank() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionCreated(TransactionId.generate(), Money.of("100", "COP"), " ", Instant.now()));
    }

    @Test
    @DisplayName("must throw exception when country is empty")
    void mustThrowExceptionWhenCountryIsEmpty() {
        assertThrows(InvalidDomainEventException.class,
                () -> new TransactionCreated(TransactionId.generate(), Money.of("100", "COP"), "", Instant.now()));
    }

    @Test
    @DisplayName("must return createdAt when occurredAt is called")
    void mustReturnCreatedAtWhenOccurredAtIsNotNull() {
        Instant occurredAt = Instant.now();
        TransactionCreated transactionCreated = new TransactionCreated(TransactionId.generate(), Money.of("100", "COP"), "COL", occurredAt);
        assertEquals(occurredAt, transactionCreated.occurredAt());
    }
}
