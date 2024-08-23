package com.github.maximtereshchenko.bloom.domain;

final class SubtractOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SubtractOperation(operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}