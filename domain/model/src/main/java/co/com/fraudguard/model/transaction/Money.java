package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.CurrencyMismatchException;
import co.com.fraudguard.model.shared.exception.InvalidAmountException;
import co.com.fraudguard.model.shared.exception.InvalidCurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

// Money: cantidad monetaria inmutable con moneda ISO 4217, scale normalizado.
// Valida: amount no null ni negativo → InvalidAmountException.
//         currency no null, ISO válida, con decimales por defecto → InvalidCurrencyException.
// Expone: add(other) — falla con CurrencyMismatchException si distinta currency.
public record Money(BigDecimal amount, Currency currency) {
    public Money {

        if (amount == null){
            throw new InvalidAmountException("Amount must be provided");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount must be non-negative");
        }
        if (currency == null) {
            throw new InvalidCurrencyException("Currency must be provided");
        }

        int digits = currency.getDefaultFractionDigits();
        if (digits < 0) {
            throw new InvalidCurrencyException("Currency " + currency + " has no fraction digits - not supported");
        }
        amount = amount.setScale(digits, RoundingMode.HALF_EVEN);
    }

    public static Money of(String amount, String currency) {
        if (currency == null) {
            throw new InvalidCurrencyException("Currency code must not be null");
        }
        if (amount == null) {
            throw new InvalidAmountException("Amount must not be null");
        }

        Currency parsedCurrency;
        try {
            parsedCurrency = Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Unknown currency code: " + currency);
        }

        BigDecimal parsedAmount;
        try {
            parsedAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Amount is not a valid decimal: " + amount);
        }

        return new Money(parsedAmount, parsedCurrency);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new CurrencyMismatchException("Can not add money of different currencies: "
                    + this.currency + " and " + other.currency);
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
