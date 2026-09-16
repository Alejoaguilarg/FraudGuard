package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.transaction.TransactionId;

import java.time.Instant;

// TransactionMarkedSuspicious: Evento inmutable emitido cuando se evalúa una transaction como sospechosa
// Valida: TransactionId, EvaluatedAt no null, Reason no null ni en blanco.
// Expone: ocurredAt() -> retorna evaluatedAt (contrato de DomainEvent)
public record TransactionMarkedSuspicious(TransactionId transactionId,
                                          String reason,
                                          Instant evaluatedAt) implements DomainEvent {

    public TransactionMarkedSuspicious {
        if (transactionId == null) {
            throw new IllegalArgumentException("transactionId must not be null");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be null or blank");
        }
        if (evaluatedAt == null) {
            throw new IllegalArgumentException("evaluatedAt must not be null");
        }
    }

    @Override
    public Instant occurredAt() {
        return evaluatedAt;
    }
}
