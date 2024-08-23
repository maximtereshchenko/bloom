package com.github.maximtereshchenko.bloom.domain;

final class BinaryOrOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.ONE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryOrOperation(operationCode.middleLeftNibble(), operationCode.middleRightNibble());
    }
}
