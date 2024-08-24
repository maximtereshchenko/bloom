package com.github.maximtereshchenko.bloom.domain;

final class AddToIndexOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.ONE &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new AddToIndexOperation(operationCode.middleLeftNibble());
    }
}
