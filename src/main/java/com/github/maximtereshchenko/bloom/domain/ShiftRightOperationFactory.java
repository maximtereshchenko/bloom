package com.github.maximtereshchenko.bloom.domain;

final class ShiftRightOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.SIX;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new ShiftRightOperation(operationCode.middleLeftNibble());
    }
}