package com.github.maximtereshchenko.bloom.domain;

final class CopyOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new CopyOperation(operationCode.middleRightNibble(), operationCode.middleLeftNibble());
    }
}
