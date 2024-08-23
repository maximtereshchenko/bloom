package com.github.maximtereshchenko.bloom.domain;

final class JumpOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.ONE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new JumpOperation(
            new Hexadecimal(
                operationCode.middleLeftNibble(),
                operationCode.middleRightNibble(),
                operationCode.lastNibble()
            )
                .memoryAddress()
        );
    }
}
