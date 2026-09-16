package co.com.fraudguard.model.transaction.event;

import co.com.fraudguard.model.transaction.Money;
import co.com.fraudguard.model.transaction.TransactionId;

import java.time.Instant;

// TransactionApproved: evento inmutable, que se emite cuando una transacción es aprobada
// Valida: TransactionId, amount, approvedAt no null
// Expone: occurredAt -> fecha de cuando se aprobó la transacción (Contrato domainEvent)
public record TransactionApproved(TransactionId transactionId,
                                  Money amount,
                                  Instant approvedAt) implements DomainEvent {

    public TransactionApproved {
        if (transactionId == null) {
            throw new IllegalArgumentException("transactionId cannot be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("amount cannot be null");
        }
        if (approvedAt == null) {
            throw new IllegalArgumentException("approvedAt cannot be null");
        }
    }

    @Override
    public Instant occurredAt() {
        return approvedAt;
    }
}
