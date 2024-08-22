package com.github.maximtereshchenko.bloom.domain;

final class DisplayOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.D;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new DisplayOperation(
            operationCode.nibble(2),
            operationCode.nibble(1),
            operationCode.nibble(3).numericValue()
        );
    }
}
