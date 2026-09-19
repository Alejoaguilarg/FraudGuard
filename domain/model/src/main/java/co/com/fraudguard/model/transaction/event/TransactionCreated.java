package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.shared.exception.InvalidDomainEventException;
import  co.com.fraudguard.model.transaction.Money;
import co.com.fraudguard.model.transaction.TransactionId;

import java.time.Instant;

// TransactionCreated: Evento inmutable emitido al crear una Transaction.
// Valida: TransactionId, Amount, Country, CreatedAt no null, country no vacío -> InvalidDomainEventException
// Expone: occurredAt() -> retorna createdAt (contrato de DomainEvent)
public record TransactionCreated(TransactionId transactionId,
                                 Money amount,
                                 String country,
                                 Instant createdAt) implements DomainEvent {
    public TransactionCreated {
        if (transactionId == null) {
            throw new InvalidDomainEventException("transactionId must not be null");
        }
        if (amount == null) {
            throw new InvalidDomainEventException("amount must not be null");
        }
        if (country == null || country.isBlank()) {
            throw new InvalidDomainEventException("country must not be null or blank");
        }
        if (createdAt == null) {
            throw new InvalidDomainEventException("createdAt must not be null");
        }
    }

    @Override
    public Instant occurredAt() {
        return createdAt;
    }
}
