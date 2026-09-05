package co.com.fraudguard.model.transaction;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
