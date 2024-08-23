package com.github.maximtereshchenko.bloom.domain;

final class ShiftLeftRegisterValueOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new ShiftLeftRegisterValueOperation(operationCode.middleLeftNibble());
    }
}