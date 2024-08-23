package com.github.maximtereshchenko.bloom.domain;

final class BinaryAndOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.TWO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryAndOperation(
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}