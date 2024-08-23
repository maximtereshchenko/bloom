package com.github.maximtereshchenko.bloom.domain;

final class LoadOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.SIX &&
            operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new LoadOperation(operationCode.middleLeftNibble());
    }
}