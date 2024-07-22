package com.plyushkin.wallet;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.util.PrefixedId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serial;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Schema(implementation = String.class, description = "WalletId")
public class WalletId extends PrefixedId {
    private static final String PREFIX = "W-";
    @Serial
    private static final long serialVersionUID = 1L;

    private WalletId(String prefix, long value) throws InvalidPrefixedIdException {
        super(prefix, value);
    }

    public static WalletId parse(String rawValue) throws InvalidWalletIdException {
        try {
            return create(parseLongFromRawValue(PREFIX, rawValue));
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidWalletIdException("Cannot parse WalletId=%s".formatted(rawValue), e);
        }
    }

    public static WalletId create(long value) throws InvalidWalletIdException {
        try {
            return new WalletId(PREFIX, value);
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidWalletIdException("Invalid WalletId: " + value, e);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(stringValue);
    }

    @StandardException
    public static class InvalidWalletIdException extends Exception {
    }
}
