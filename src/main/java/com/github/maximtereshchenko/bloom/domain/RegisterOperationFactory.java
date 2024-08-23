package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

final class RegisterOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        var first = operationCode.nibble(0);
        var middleLeft = operationCode.nibble(1);
        var middleRight = operationCode.nibble(2);
        var last = operationCode.nibble(3);
        return switch (first) {
            case SIX -> setRegisterValue(middleLeft, middleRight, last);
            case SEVEN -> addValueToRegister(middleLeft, middleRight, last);
            case EIGHT -> switch (last) {
                case ZERO -> copyRegisterValue(middleLeft, middleRight);
                case ONE -> binaryOr(middleLeft, middleRight);
                case TWO -> binaryAnd(middleLeft, middleRight);
                case THREE -> binaryXor(middleLeft, middleRight);
                case FOUR -> sumRegisterValues(middleLeft, middleRight);
                default -> Optional.empty();
            };
            default -> Optional.empty();
        };
    }

    private Optional<Operation> setRegisterValue(
        HexadecimalSymbol registerName,
        HexadecimalSymbol firstValueComponent,
        HexadecimalSymbol secondValueComponent
    ) {
        return singleRegisterOperation(registerName, firstValueComponent, secondValueComponent, Register::set);
    }

    private Optional<Operation> addValueToRegister(
        HexadecimalSymbol registerName,
        HexadecimalSymbol firstValueComponent,
        HexadecimalSymbol secondValueComponent
    ) {
        return singleRegisterOperation(
            registerName,
            firstValueComponent,
            secondValueComponent,
            (register, value) -> register.set(register.get().sum(value))
        );
    }

    private Optional<Operation> singleRegisterOperation(
        HexadecimalSymbol registerName,
        HexadecimalSymbol firstValueComponent,
        HexadecimalSymbol secondValueComponent,
        BiConsumer<Register<Byte>, Byte> consumer
    ) {
        return Optional.of(
            new RegisterOperation(
                registerName,
                registerName,
                (first, unused) ->
                    consumer.accept(first, new Hexadecimal(firstValueComponent, secondValueComponent).asByte())
            )
        );
    }

    private Optional<Operation> copyRegisterValue(HexadecimalSymbol to, HexadecimalSymbol from) {
        return Optional.of(new RegisterOperation(to, from, (first, second) -> first.set(second.get())));
    }

    private Optional<Operation> binaryOr(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        return valueModificationOperation(firstRegisterName, secondRegisterName, Byte::or);
    }

    private Optional<Operation> binaryAnd(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        return valueModificationOperation(firstRegisterName, secondRegisterName, Byte::and);
    }

    private Optional<Operation> binaryXor(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        return valueModificationOperation(firstRegisterName, secondRegisterName, Byte::xor);
    }

    private Optional<Operation> sumRegisterValues(
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        return valueModificationOperation(firstRegisterName, secondRegisterName, Byte::sum);
    }

    private Optional<Operation> valueModificationOperation(
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName,
        BinaryOperator<Byte> operator
    ) {
        return Optional.of(
            new RegisterOperation(
                firstRegisterName,
                secondRegisterName,
                (first, second) -> first.set(operator.apply(first.get(), second.get()))
            )
        );
    }
}
