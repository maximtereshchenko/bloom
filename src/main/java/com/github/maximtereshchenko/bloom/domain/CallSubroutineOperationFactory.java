package com.github.maximtereshchenko.bloom.domain;

final class CallSubroutineOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.TWO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new CallSubroutineOperation(
            new Hexadecimal(
                operationCode.middleLeftNibble(),
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            )
                .memoryAddress()
        );
    }
}
