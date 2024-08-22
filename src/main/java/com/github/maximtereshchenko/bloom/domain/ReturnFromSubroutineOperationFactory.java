package com.github.maximtereshchenko.bloom.domain;

final class ReturnFromSubroutineOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.ZERO &&
            operationCode.nibble(1) == HexadecimalSymbol.ZERO &&
            operationCode.nibble(2) == HexadecimalSymbol.E &&
            operationCode.nibble(3) == HexadecimalSymbol.E;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new ReturnFromSubroutineOperation();
    }
}
