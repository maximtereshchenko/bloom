package com.github.maximtereshchenko.bloom.domain;

final class ConvertToBinaryCodedDecimalOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.THREE &&
            operationCode.lastNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new ConvertToBinaryCodedDecimalOperation(operationCode.middleLeftNibble());
    }
}
