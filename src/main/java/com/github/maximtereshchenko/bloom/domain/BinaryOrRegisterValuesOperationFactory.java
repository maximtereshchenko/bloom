package com.github.maximtereshchenko.bloom.domain;

final class BinaryOrRegisterValuesOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.ONE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryOrRegisterValuesOperation(operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}
