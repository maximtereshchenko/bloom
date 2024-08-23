package com.github.maximtereshchenko.bloom.domain;

final class SkipIfSingleRegisterValueNotEqualOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.FOUR;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfSingleRegisterValueNotEqualOperation(
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}