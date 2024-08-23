package com.github.maximtereshchenko.bloom.domain;

final class AddOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.SEVEN;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new AddOperation(
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}
