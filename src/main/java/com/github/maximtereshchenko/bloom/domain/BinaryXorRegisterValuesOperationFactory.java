package com.github.maximtereshchenko.bloom.domain;

final class BinaryXorRegisterValuesOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryXorRegisterValuesOperation(
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}