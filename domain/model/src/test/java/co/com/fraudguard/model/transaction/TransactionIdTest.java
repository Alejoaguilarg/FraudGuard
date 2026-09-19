package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.InvalidTransactionIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionIdTest {

    @Test
    void mustGenerateId() {
        final TransactionId id = TransactionId.generate();
        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    void mustGenerateDifferentIds() {
        final TransactionId generated1 = TransactionId.generate();
        final TransactionId generated2 = TransactionId.generate();

        assertNotNull(generated2);
        assertNotNull(generated1);
        assertNotEquals(generated1, generated2);
    }

    @Test
    @DisplayName("rejects null value")
    void mustRejectNullValue() {
        assertThrows(InvalidTransactionIdException.class, () -> new TransactionId(null));
    }
}
