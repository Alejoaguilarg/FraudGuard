package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.transaction.TransactionId;

import java.time.Instant;

// TransactionBlocked: Evento inmutable emitido cuando se bloquea lo transacción
// Valida: transactionId, occurredAt no null y reason no null ni en blanco
//expone:occurredAt -> retorna occurredAt (contrato DomainEvent)
public record TransactionBlocked(TransactionId transactionId,
                                 String reason,
                                 Instant occurredAt) implements DomainEvent {

    public TransactionBlocked {
        if (transactionId == null) {
            throw new IllegalArgumentException("transactionId must not be null");
        }
        if (reason == null ||  reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be null or blank");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("occurredAt must not be null");
        }
    }
}
