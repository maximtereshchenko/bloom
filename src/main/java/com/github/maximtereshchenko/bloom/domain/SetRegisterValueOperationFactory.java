package com.github.maximtereshchenko.bloom.domain;

final class SetRegisterValueOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.SIX;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SetRegisterValueOperation(
            operationCode.middleLeftNibble(),
            new Hexadecimal(operationCode.middleRightNibble(), operationCode.lastNibble()).unsignedByte()
        );
    }
}
