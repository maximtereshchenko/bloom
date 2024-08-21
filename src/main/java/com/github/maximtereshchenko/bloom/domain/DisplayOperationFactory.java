package com.github.maximtereshchenko.bloom.domain;

final class DisplayOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.D;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new DisplayOperation(
            operationCode.nibble(2),
            operationCode.nibble(1),
            operationCode.nibble(3).numericValue()
        );
    }
}
