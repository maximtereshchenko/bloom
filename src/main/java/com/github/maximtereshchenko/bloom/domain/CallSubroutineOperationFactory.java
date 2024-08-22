package com.github.maximtereshchenko.bloom.domain;

final class CallSubroutineOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.TWO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new CallSubroutineOperation(
            new Hexadecimal(
                operationCode.nibble(1),
                operationCode.nibble(2),
                operationCode.nibble(3)
            )
                .memoryAddress()
        );
    }
}
