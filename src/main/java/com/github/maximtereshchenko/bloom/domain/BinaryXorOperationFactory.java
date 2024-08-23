package com.github.maximtereshchenko.bloom.domain;

final class BinaryXorOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.EIGHT &&
            operationCode.lastNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new BinaryXorOperation(
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}