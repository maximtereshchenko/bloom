package com.github.maximtereshchenko.bloom.domain;

final class SetIndexRegisterValueOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.A;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SetIndexRegisterValueOperation(
            new Hexadecimal(
                operationCode.nibble(1),
                operationCode.nibble(2),
                operationCode.nibble(3)
            )
                .memoryAddress()
        );
    }
}
