package com.plyushkin.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serializable;
import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@MappedSuperclass
@Getter
public class PrefixedId implements Serializable {
    private static final int MAX_RADIX = 36;
    private static final int ID_LENGTH = 19;

    @Getter(PRIVATE)
    @Column(name = "id", updatable = false)
    protected long value;

    @Transient
    protected String stringValue;

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    protected PrefixedId(String prefix) throws InvalidPrefixedIdException {
        this(prefix, System.currentTimeMillis());
    }

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    protected PrefixedId(String prefix,
                         long value) throws InvalidPrefixedIdException {
        if (value < 0) {
            throw new InvalidPrefixedIdException("Value cannot be less than zero: " + value);
        }
        this.stringValue = prefix + Long.toString(value, MAX_RADIX);
        this.value = value;
    }

    public static long parseLongFromRawValue(String prefix, String rawValue) throws InvalidPrefixedIdException {
        if (rawValue == null || prefix == null) {
            throw new InvalidPrefixedIdException("Value=%s or prefix=%s is null".formatted(rawValue, prefix));
        }
        final var split = rawValue.split(prefix);
        if (split.length != 2) {
            throw new InvalidPrefixedIdException(
                    "Unexpected split by prefix=%s: %s"
                            .formatted(prefix, Arrays.toString(split))
            );
        }
        try {
            return Long.parseLong(split[1], MAX_RADIX);
        } catch (NumberFormatException e) {
            throw new InvalidPrefixedIdException(
                    "Couldn't parse value=%s for radix=%s"
                            .formatted(rawValue, MAX_RADIX), e
            );
        }
    }

    @StandardException
    public static class InvalidPrefixedIdException extends Exception {
    }
}
