package com.github.maximtereshchenko.bloom.domain;

final class SumRegisterValuesOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.FOUR;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SumRegisterValuesOperation(operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}