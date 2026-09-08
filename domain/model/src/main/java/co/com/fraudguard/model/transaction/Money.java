package co.com.fraudguard.model.transaction;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    public Money {

        if(amount == null){
            throw new IllegalArgumentException("Amount must be provided");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (currency == null || currency.getCurrencyCode().isBlank()) {
            throw new IllegalArgumentException("Currency must be provided");
        }
    }

    public static Money of(String amount, String currency) {
        if (currency == null ) {
            throw new IllegalArgumentException("Currency code must not be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        return new Money(new BigDecimal(amount), Currency.getInstance(currency));
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Can not add money of different currencies: "
                    + this.currency + " and " + other.currency);
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
