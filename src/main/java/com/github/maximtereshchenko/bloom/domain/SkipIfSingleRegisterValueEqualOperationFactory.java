package com.github.maximtereshchenko.bloom.domain;

final class SkipIfSingleRegisterValueEqualOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.THREE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfSingleRegisterValueEqualOperation(
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}