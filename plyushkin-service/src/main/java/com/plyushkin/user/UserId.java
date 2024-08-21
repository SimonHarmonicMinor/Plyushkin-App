package com.plyushkin.user;

import com.plyushkin.infra.PrefixedId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serial;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Schema(implementation = String.class, description = "UserId")
public class UserId extends PrefixedId {
    private static final String PREFIX = "U-";
    @Serial
    private static final long serialVersionUID = 1;

    protected UserId(long value)
            throws InvalidPrefixedIdException {
        super(value);
    }

    public static UserId parse(String rawValue) throws InvalidUserIdException {
        try {
            return create(parseLongFromRawValue(PREFIX, rawValue));
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidUserIdException("Cannot parse UserId=%s".formatted(rawValue), e);
        }
    }

    public static UserId create(long value) throws InvalidUserIdException {
        try {
            return new UserId(value);
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidUserIdException(
                    "Invalid UserId", e
            );
        }
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @StandardException
    public static class InvalidUserIdException extends Exception {
    }
}
