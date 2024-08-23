package com.github.maximtereshchenko.bloom.domain;

final class ClearDisplayOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.ZERO &&
            operationCode.middleLeftNibble() == HexadecimalSymbol.ZERO &&
            operationCode.middleRightNibble() == HexadecimalSymbol.E &&
            operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new ClearDisplayOperation();
    }
}
