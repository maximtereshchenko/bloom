package com.github.maximtereshchenko.bloom.domain;

final class BinaryOrOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.EIGHT &&
            operationCode.nibble(3) == HexadecimalSymbol.ONE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryOrOperation(operationCode.nibble(1), operationCode.nibble(2));
    }
}
