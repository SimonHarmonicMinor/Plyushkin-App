package com.plyushkin.budget;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serial;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
@SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
public abstract class AbstractNumber implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    protected AbstractNumber(long value) throws InvalidNumberException {
        if (value <= 0) {
            throw new InvalidNumberException(
                    "Value should be positive but it is: " + value
            );
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @StandardException
    public static class InvalidNumberException extends Exception {
    }
}
