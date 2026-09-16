package co.com.fraudguard.model.transaction.event;

import java.time.Instant;

// DomainEvent: Sealed interface.Contrato para todo evento de dominio de transaction.
// Expone: OcurredAt() - timestamp del hecho de negocio.
// permitidos: TransactionCreated, TransactionMarkedSuspicious, TransactionBlocked, TransactionApproved
public sealed interface DomainEvent
        permits TransactionCreated,
        TransactionMarkedSuspicious,
        TransactionBlocked,
        TransactionApproved {
    Instant occurredAt();
}
