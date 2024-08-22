package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;
import java.util.function.Predicate;

final class SkipConditionallyOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        var first = operationCode.nibble(0);
        var middleLeft = operationCode.nibble(1);
        var middleRight = operationCode.nibble(2);
        var last = operationCode.nibble(3);
        return skipIfRegisterValues(first, middleLeft, middleRight, last)
            .or(() -> skipIfSingleRegisterValue(first, middleLeft, middleRight, last));
    }

    private Optional<Operation> skipIfRegisterValues(
        HexadecimalSymbol first,
        HexadecimalSymbol middleLeft,
        HexadecimalSymbol middleRight,
        HexadecimalSymbol last
    ) {
        if (last != HexadecimalSymbol.ZERO) {
            return Optional.empty();
        }
        return switch (first) {
            case FIVE -> skipConditionally(ifRegisterValuesEquals(middleLeft, middleRight));
            case NINE -> skipConditionally(ifRegisterValuesEquals(middleLeft, middleRight).negate());
            default -> Optional.empty();
        };
    }

    private Optional<Operation> skipIfSingleRegisterValue(
        HexadecimalSymbol first,
        HexadecimalSymbol middleLeft,
        HexadecimalSymbol middleRight,
        HexadecimalSymbol last
    ) {
        return switch (first) {
            case THREE -> skipConditionally(ifSingleRegisterValueEquals(middleLeft, middleRight, last));
            case FOUR -> skipConditionally(ifSingleRegisterValueEquals(middleLeft, middleRight, last).negate());
            default -> Optional.empty();
        };
    }

    private Optional<Operation> skipConditionally(Predicate<Registers> predicate) {
        return Optional.of(new SkipConditionallyOperation(predicate));
    }

    private Predicate<Registers> ifSingleRegisterValueEquals(
        HexadecimalSymbol registerName,
        HexadecimalSymbol firstValueComponent,
        HexadecimalSymbol secondValueComponent
    ) {
        return registers -> registers.generalPurpose(registerName)
            .get()
            .equals(new Hexadecimal(firstValueComponent, secondValueComponent).asByte());
    }

    private Predicate<Registers> ifRegisterValuesEquals(
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        return registers -> registers.generalPurpose(firstRegisterName)
            .get()
            .equals(registers.generalPurpose(secondRegisterName).get());
    }
}
