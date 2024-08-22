package com.github.maximtereshchenko.bloom.domain;

final class CopyRegisterValueOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.EIGHT &&
            operationCode.nibble(3) == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new CopyRegisterValueOperation(operationCode.nibble(2), operationCode.nibble(1));
    }
}
