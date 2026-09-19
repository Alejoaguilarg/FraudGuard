package co.com.fraudguard.model.transaction;

// TransactionStatus: ciclo de vida de una Transaction en el dominio.
// PENDING → recién creada, sin evaluar.
// REVIEW → marcada como sospechosa por reglas (evaluate).
// BLOCKED → bloqueada por país sancionado o score crítico. Estado final.
// APPROVED → aprobada tras aplicar score no crítico. Estado final.
public enum TransactionStatus {
    PENDING,
    REVIEW,
    BLOCKED,
    APPROVED
}
