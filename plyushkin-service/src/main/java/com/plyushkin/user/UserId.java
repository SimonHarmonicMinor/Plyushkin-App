package com.plyushkin.user;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.util.PrefixedId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;

import java.io.Serial;

import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Schema(implementation = String.class, description = "UserId")
public class UserId extends PrefixedId {

    @Serial
    private static final long serialVersionUID = 1;

    protected UserId(String prefix) throws InvalidPrefixedIdException {
        super(prefix);
    }

    protected UserId(String prefix, String value)
            throws InvalidPrefixedIdException {
        super(prefix, value);
    }

    public static UserId create(String value) throws InvalidUserIdException {
        try {
            return new UserId("U", value);
        } catch (InvalidPrefixedIdException e) {
            throw new InvalidUserIdException(
                    "Invalid UserId", e
            );
        }
    }

    public static UserId createRandom() {
        try {
            return new UserId("U");
        } catch (InvalidPrefixedIdException e) {
            throw new IllegalArgumentException("Invalid UserId", e);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @StandardException
    public static class InvalidUserIdException extends Exception {
    }
}
