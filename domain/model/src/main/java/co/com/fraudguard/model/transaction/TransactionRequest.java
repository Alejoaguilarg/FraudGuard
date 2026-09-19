package co.com.fraudguard.model.transaction;

import co.com.fraudguard.model.shared.exception.InvalidAmountException;
import co.com.fraudguard.model.shared.exception.InvalidCountryException;

// TransactionRequest: DTO de entrada inmutable para crear una Transaction.
// Valida: amount no null → InvalidAmountException.
//         originCountry no null ni blank → InvalidCountryException.
// Expone: los accessors autogenerados del record.
public record TransactionRequest(Money amount, String originCountry) {
    public TransactionRequest {
        if (amount == null) throw new InvalidAmountException("TransactionRequest: amount must not be null");
        if (originCountry == null || originCountry.isBlank()) {
            throw new InvalidCountryException("TransactionRequest: originCountry must not be null or blank");
        }
    }
}
