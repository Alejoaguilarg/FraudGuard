package co.com.fraudguard.model.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Money")
class MoneyTest {

    @Nested
    @DisplayName("creation")
    class Creation {

        @Test
        @DisplayName("rejects negative amount")
        void mustBePositive() {
            assertThrows(IllegalArgumentException.class, () -> Money.of("-1", "USD"));
        }

        @Test
        void mustAcceptZeroAmount() {
            final Money actual = Money.of("0", "USD");
            assertEquals(0, BigDecimal.ZERO.compareTo(actual.amount()));
        }

        @Test
        @DisplayName("handles floating point correctly")
        void mustHandleFloatingPoint() {
            Money result = Money.of("0.1", "USD").add(Money.of("0.2", "USD"));
            assertEquals(0, new BigDecimal("0.3").compareTo(result.amount()));
        }

        @Test
        @DisplayName("rejects null currency")
        void mustHaveCurrency() {
            assertThrows(IllegalArgumentException.class, () -> Money.of("1", null));
        }

        @Test
        @DisplayName("rejects empty currency")
        void mustHaveCurrencyNotEmpty() {
            assertThrows(IllegalArgumentException.class, () -> Money.of("1", ""));
        }

        @Test
        void mustRejectInvalidCurrency() {
            assertThrows(IllegalArgumentException.class, () -> Money.of("1", "invalid"));
        }
    }

    @Nested
    @DisplayName("add")
    class Add {

        @Test
        @DisplayName("sums amounts of same currency")
        void mustAddSameCurrency() {
            final Money actual = Money.of("100", "USD").add(Money.of("50", "USD"));
            assertEquals(0, new BigDecimal("150").compareTo(actual.amount()));
            assertEquals(Currency.getInstance("USD"), actual.currency());
        }

        @Test
        @DisplayName("rejects different currencies")
        void mustRejectDifferentCurrency() {
            assertThrows(IllegalArgumentException.class,
                    () -> Money.of("1", "USD").add(Money.of("1", "EUR")));
        }

        @Test
        @DisplayName("does not mutate original")
        void mustNotMutateOriginal() {
            final Money original = Money.of("100", "USD");
            final Money result = original.add(Money.of("50", "USD"));
            assertEquals(0, new BigDecimal(100).compareTo(original.amount()));
            assertEquals(0, new BigDecimal("150").compareTo(result.amount()));
            assertNotSame(original, result);
        }
    }
}