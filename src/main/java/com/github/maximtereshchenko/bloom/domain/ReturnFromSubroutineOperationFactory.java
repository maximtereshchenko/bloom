package com.github.maximtereshchenko.bloom.domain;

final class ReturnFromSubroutineOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.ZERO &&
            operationCode.middleLeftNibble() == HexadecimalSymbol.ZERO &&
            operationCode.middleRightNibble() == HexadecimalSymbol.E &&
            operationCode.lastNibble() == HexadecimalSymbol.E;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new ReturnFromSubroutineOperation();
    }
}
