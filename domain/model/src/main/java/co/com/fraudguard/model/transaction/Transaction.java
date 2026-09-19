package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.InvalidRiskScoreException;
import co.com.fraudguard.model.shared.exception.InvalidTransactionRequestException;
import co.com.fraudguard.model.shared.exception.TransactionAlreadyClosedException;
import co.com.fraudguard.model.shared.exception.TransactionAlreadyEvaluatedException;
import co.com.fraudguard.model.transaction.event.DomainEvent;
import co.com.fraudguard.model.transaction.event.TransactionApproved;
import co.com.fraudguard.model.transaction.event.TransactionBlocked;
import co.com.fraudguard.model.transaction.event.TransactionCreated;
import co.com.fraudguard.model.transaction.event.TransactionMarkedSuspicious;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static co.com.fraudguard.model.transaction.TransactionStatus.PENDING;
import static co.com.fraudguard.model.transaction.TransactionStatus.REVIEW;

// Transaction: aggregate root del dominio de FraudGuard.
// Encapsula estado (status, riskScore) y reglas de negocio (evaluate, applyScore).
// Registra hechos de negocio como DomainEvents.
// Valida: creación solo vía from(request); request no null → InvalidTransactionRequestException.
// Expone: from(request), evaluate(), applyScore(score), getDomainEvents(), clearEvents(), getters.
// NO expone: setters, ni acceso mutable a la lista de eventos.
@Getter
public class Transaction {
    private final TransactionId id;
    private final Money amount;
    private final String originCountry;
    private TransactionStatus status;
    private RiskScore riskScore;
    @Getter(AccessLevel.NONE)
    private final List<DomainEvent> events;

    // TODO(config-when-needed): extraer a RiskThresholds policy cuando negocio
    // pida cambiar sin redeploy. Ver ADR-003 para el patrón.
    private static final BigDecimal HIGH_AMOUNT_THRESHOLD = new BigDecimal("10000");
    private static final Set<String> SANCTIONED = Set.of("IRN", "PRK", "SYR");

    private Transaction(TransactionId id, Money amount, String originCountry) {
        this.id = id;
        this.amount = amount;
        this.originCountry = originCountry;
        this.status = PENDING;
        this.riskScore = null;
        this.events = new ArrayList<>();
    }

    public static Transaction from(TransactionRequest request) {
        if (request == null) throw new InvalidTransactionRequestException("TransactionRequest: request must not be null");

        Transaction tx = new Transaction(
                TransactionId.generate(),
                request.amount(),
                request.originCountry()
        );
        tx.events.add(new TransactionCreated(tx.id,
                tx.amount,
                tx.originCountry,
                Instant.now()));
        return tx;
    }

    public void evaluate() {
        if (this.status != TransactionStatus.PENDING) {
            throw new TransactionAlreadyEvaluatedException("Transaction: cannot evaluate - current status is "
            + this.status);
        }

        if(SANCTIONED.contains(this.originCountry)) {
            this.status = TransactionStatus.BLOCKED;
            events.add(new TransactionBlocked(this.id, "Sanctioned country", Instant.now()));
            return;
        }

        if (this.amount.amount().compareTo(HIGH_AMOUNT_THRESHOLD) > 0) {
            this.status = REVIEW;
            events.add(new TransactionMarkedSuspicious(this.id, "High amount", Instant.now()));
        }
    }

    public void applyScore(RiskScore score) {

        if (score == null) {
            throw new InvalidRiskScoreException("Transaction: Risk score cannot be null");
        }
        // fail fast
        if (this.status == TransactionStatus.BLOCKED || this.status == TransactionStatus.APPROVED) {
            throw new TransactionAlreadyClosedException("Transaction: cannot apply score to "
            + this.status + " transaction");
        }

        this.riskScore = score;

        if (score.isCritical()) {
            this.status = TransactionStatus.BLOCKED;
            events.add(new TransactionBlocked(this.id, "Critical risk score", Instant.now()));
            return;
        }

        this.status = TransactionStatus.APPROVED;
        events.add(new TransactionApproved(this.id, amount, Instant.now()));
    }

    public List<DomainEvent> getDomainEvents() {
        return List.copyOf(events);
    }

    public void clearEvents() {
        this.events.clear();
    }
}
