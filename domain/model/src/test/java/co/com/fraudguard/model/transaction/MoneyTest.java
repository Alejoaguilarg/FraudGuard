package co.com.fraudguard.model.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Money")
class MoneyTest {

    @Nested
    @DisplayName("creation")
    class Creation {

        @Test
        @DisplayName("rejects negative amount")
        void mustBePositive() {
            assertThrows(IllegalArgumentException.class, () -> new Money(-1, "USD"));
        }

        @Test
        @DisplayName("rejects null currency")
        void mustHaveCurrency() {
            assertThrows(IllegalArgumentException.class, () -> new Money(1, null));
        }

        @Test
        @DisplayName("rejects empty currency")
        void mustHaveCurrencyNotEmpty() {
            assertThrows(IllegalArgumentException.class, () -> new Money(1, ""));
        }
    }

    @Nested
    @DisplayName("add")
    class Add {

        @Test
        @DisplayName("sums amounts of same currency")
        void mustAddSameCurrency() {
            final Money actual = new Money(1, "USD").add(new Money(1, "USD"));
            assertEquals(2, actual.amount());
            assertEquals("USD", actual.currency());
        }

        @Test
        @DisplayName("rejects different currencies")
        void mustRejectDifferentCurrency() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Money(1, "USD").add(new Money(1, "EUR")));
        }

        @Test
        @DisplayName("does not mutate original")
        void mustNotMutateOriginal() {
            final Money original = new Money(1, "USD");
            final Money result = original.add(new Money(1, "USD"));
            assertEquals(1, original.amount());
            assertNotSame(original, result);
        }
    }
}