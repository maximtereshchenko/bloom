package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;

final class RegisterOperationFactory implements OperationFactory {

    @Override
    public Optional<Operation> operation(OperationCode operationCode) {
        var first = operationCode.nibble(0);
        var middleLeft = operationCode.nibble(1);
        var middleRight = operationCode.nibble(2);
        var last = operationCode.nibble(3);
        if (first == HexadecimalSymbol.SIX) {
            return setRegisterValue(middleLeft, new Hexadecimal(middleRight, last).asByte());
        }
        if (first == HexadecimalSymbol.SEVEN) {
            return addValueToRegister(middleLeft, new Hexadecimal(middleRight, last).asByte());
        }
        if (first == HexadecimalSymbol.EIGHT && last == HexadecimalSymbol.ZERO) {
            return copyRegisterValue(middleLeft, middleRight);
        }
        if (first == HexadecimalSymbol.EIGHT && last == HexadecimalSymbol.ONE) {
            return binaryOr(middleLeft, middleRight);
        }
        return Optional.empty();
    }

    private Optional<Operation> setRegisterValue(HexadecimalSymbol registerName, Byte value) {
        return Optional.of(new RegisterOperation(registerName, registerName, (first, unused) -> first.set(value)));
    }

    private Optional<Operation> addValueToRegister(HexadecimalSymbol registerName, Byte value) {
        return Optional.of(new RegisterOperation(
            registerName,
            registerName,
            (first, unused) -> first.set(first.get().sum(value))
        ));
    }

    private Optional<Operation> copyRegisterValue(HexadecimalSymbol to, HexadecimalSymbol from) {
        return Optional.of(new RegisterOperation(to, from, (first, second) -> first.set(second.get())));
    }

    private Optional<Operation> binaryOr(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        return Optional.of(new RegisterOperation(
            firstRegisterName,
            secondRegisterName,
            (first, second) -> first.set(first.get().or(second.get()))
        ));
    }
}
