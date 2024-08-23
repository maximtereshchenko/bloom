package com.github.maximtereshchenko.bloom.domain;

final class SetIndexRegisterValueOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.A;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SetIndexRegisterValueOperation(
            new Hexadecimal(
                operationCode.middleLeftNibble(),
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            )
                .memoryAddress()
        );
    }
}
