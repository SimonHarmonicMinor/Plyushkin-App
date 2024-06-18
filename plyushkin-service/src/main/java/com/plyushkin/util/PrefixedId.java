package com.plyushkin.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@MappedSuperclass
@Getter
public class PrefixedId implements Serializable {

    private static final int ID_LENGTH = 19;

    @Column(name = "id", updatable = false)
    protected String value;

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    protected PrefixedId(String prefix) throws InvalidPrefixedIdException {
        this(
                prefix,
                prefix
                        + String.format(
                        "%19d",
                        ThreadLocalRandom.current()
                                .nextLong(0, Long.MAX_VALUE)
                )
        );
    }

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    protected PrefixedId(String prefix,
                         String value) throws InvalidPrefixedIdException {
        if (!value.startsWith(prefix)) {
            throw new InvalidPrefixedIdException(
                    "Id must start with the prefix %s but actual value is %s"
                            .formatted(prefix, value)
            );
        }
        if (value.length() != ID_LENGTH) {
            throw new InvalidPrefixedIdException(
                    "Id length must be %d but actual value is %s"
                            .formatted(ID_LENGTH, value)
            );
        }
        try {
            long number = Long.parseLong(value.substring(prefix.length()));
            if (number < 0) {
                throw new InvalidPrefixedIdException(
                        "Id value must be positive but it is %s"
                                .formatted(value)
                );
            }
            this.value = value;
        } catch (NumberFormatException e) {
            throw new InvalidPrefixedIdException(
                    "Cannot parse number from id value %s"
                            .formatted(value),
                    e
            );
        }
    }

    @StandardException
    public static class InvalidPrefixedIdException extends Exception {
    }
}
