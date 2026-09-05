package co.com.fraudguard.model.transaction;

public record Money(double amount, String currency) {
    public Money {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency must be provided");
        }
    }
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must be the same");
        }
        return new Money(this.amount + other.amount, this.currency);
    }
}
