package com.plyushkin.wallet;

import com.plyushkin.shared.PrefixedId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serial;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Schema(implementation = String.class, description = "WalletId")
@NoArgsConstructor(access = PROTECTED)
public class WalletId extends PrefixedId {
    private static final String PREFIX = "W-";
    @Serial
    private static final long serialVersionUID = 1L;

    private WalletId(long value) throws InvalidPrefixedIdException {
        super(value);
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
            return new WalletId(value);
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidWalletIdException("Invalid WalletId: " + value, e);
        }
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @StandardException
    public static class InvalidWalletIdException extends Exception {
    }
}
