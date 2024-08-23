package com.github.maximtereshchenko.bloom.domain;

final class DisplayOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.D;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new DisplayOperation(
            operationCode.middleRightNibble(),
            operationCode.middleLeftNibble(),
            operationCode.lastNibble().numericValue()
        );
    }
}
