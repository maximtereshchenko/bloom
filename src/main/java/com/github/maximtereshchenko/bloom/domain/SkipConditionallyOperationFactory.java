package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;
import java.util.function.Predicate;

final class SkipConditionallyOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        var first = operationCode.nibble(0);
        return skipConditionallyForSingleRegister(operationCode, first)
            .or(() -> skipConditionallyForRegisters(operationCode, first));
    }

    private Optional<Operation> skipConditionallyForRegisters(OperationCode operationCode, HexadecimalSymbol first) {
        if (operationCode.nibble(3) != HexadecimalSymbol.ZERO) {
            return Optional.empty();
        }
        if (first == HexadecimalSymbol.FIVE) {
            return skipConditionally(ifRegisterValuesEquals(operationCode));
        }
        if (first == HexadecimalSymbol.NINE) {
            return skipConditionally(ifRegisterValuesEquals(operationCode).negate());
        }
        return Optional.empty();
    }

    private Optional<Operation> skipConditionallyForSingleRegister(
        OperationCode operationCode,
        HexadecimalSymbol firstNibble
    ) {
        if (firstNibble == HexadecimalSymbol.THREE) {
            return skipConditionally(ifSingleRegisterValueEquals(operationCode));
        }
        if (firstNibble == HexadecimalSymbol.FOUR) {
            return skipConditionally(ifSingleRegisterValueEquals(operationCode).negate());
        }
        return Optional.empty();
    }

    private Optional<Operation> skipConditionally(Predicate<Registers> predicate) {
        return Optional.of(new SkipConditionallyOperation(predicate));
    }

    private Predicate<Registers> ifSingleRegisterValueEquals(OperationCode operationCode) {
        return registers -> registerValue(registers, operationCode, 1)
            .equals(
                new Hexadecimal(operationCode.nibble(2), operationCode.nibble(3))
                    .asByte()
            );
    }

    private Predicate<Registers> ifRegisterValuesEquals(OperationCode operationCode) {
        return registers -> registerValue(registers, operationCode, 1)
            .equals(registerValue(registers, operationCode, 2));
    }

    private Byte registerValue(Registers registers, OperationCode operationCode, int index) {
        return registers.generalPurpose(operationCode.nibble(index)).get();
    }
}
