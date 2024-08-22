package com.plyushkin.shared;

import com.plyushkin.shared.budget.domain.AbstractNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.StandardException;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
@Getter
@Schema(implementation = Long.class, description = "IncomeCategoryNumber", minimum = "1")
public class IncomeCategoryNumber implements AbstractNumber<IncomeCategoryNumber> {
    private final long value;

    public static IncomeCategoryNumber create(long value) throws InvalidIncomeCategoryNumberException {
        if (value <= 0) {
            throw new InvalidIncomeCategoryNumberException(
                    "Value should be positive but it is: " + value
            );
        }
        return new IncomeCategoryNumber(value);
    }

    @Override
    public IncomeCategoryNumber increment() {
        return new IncomeCategoryNumber(this.value + 1);
    }

    @StandardException
    public static class InvalidIncomeCategoryNumberException extends Exception {

    }
}
