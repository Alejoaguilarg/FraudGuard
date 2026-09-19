package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.InvalidTransactionIdException;

import java.util.UUID;

// TransactionId: Identificador uuid único para cada transacción.
// Valida: value no null → InvalidTransactionIdException.
// Expone: generate() — retorna TransactionId.
public record TransactionId(UUID value) {

    public TransactionId {
        if (value == null) {
            throw new InvalidTransactionIdException("TransactionId value must not be null");
        }
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }
}
