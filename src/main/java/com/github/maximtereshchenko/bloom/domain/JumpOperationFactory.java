package com.github.maximtereshchenko.bloom.domain;

final class JumpOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.ONE;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new JumpOperation(
            new Hexadecimal(
                operationCode.nibble(1),
                operationCode.nibble(2),
                operationCode.nibble(3)
            )
                .memoryAddress()
        );
    }
}
