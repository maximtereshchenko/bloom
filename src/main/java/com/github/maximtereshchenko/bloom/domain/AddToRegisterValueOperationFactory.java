package com.github.maximtereshchenko.bloom.domain;

final class AddToRegisterValueOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.SEVEN;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new AddToRegisterValueOperation(
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}
