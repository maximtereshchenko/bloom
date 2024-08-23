package com.github.maximtereshchenko.bloom.domain;

final class SetOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.SIX;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SetOperation(
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}
