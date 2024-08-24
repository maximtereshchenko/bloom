package com.github.maximtereshchenko.bloom.domain;

final class NegativeSubtractOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.SEVEN;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new NegativeSubtractOperation(operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}