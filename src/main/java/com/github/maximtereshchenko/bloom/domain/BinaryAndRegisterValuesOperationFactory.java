package com.github.maximtereshchenko.bloom.domain;

final class BinaryAndRegisterValuesOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.TWO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryAndRegisterValuesOperation(
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}