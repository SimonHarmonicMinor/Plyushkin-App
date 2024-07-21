package com.plyushkin.wallet;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.util.PrefixedId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Schema(implementation = String.class, description = "WalletId")
public class WalletId extends PrefixedId {

    private WalletId(String prefix) throws InvalidPrefixedIdException {
        super(prefix);
    }

    private WalletId(String prefix, String value) throws InvalidPrefixedIdException {
        super(prefix, value);
    }

    public static WalletId createRandom() {
        try {
            return new WalletId("WA");
        } catch (InvalidPrefixedIdException e) {
            throw new IllegalArgumentException("Invalid random WalletId", e);
        }
    }

    public static WalletId create(String value) throws InvalidWalletIdException {
        try {
            return new WalletId("WA", value);
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidWalletIdException("Invalid WalletId: " + value, e, value);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Getter
    public static class InvalidWalletIdException extends Exception {
        private final String passedValue;

        public InvalidWalletIdException(String message, Throwable cause, String passedValue) {
            super(message, cause);
            this.passedValue = passedValue;
        }
    }
}
