package com.github.maximtereshchenko.bloom.domain;

final class StoreOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.FIVE &&
            operationCode.lastNibble() == HexadecimalSymbol.FIVE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new StoreOperation(operationCode.middleLeftNibble());
    }
}