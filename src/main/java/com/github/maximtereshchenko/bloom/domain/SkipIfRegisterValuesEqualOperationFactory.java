package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesEqualOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.FIVE &&
            operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfRegisterValuesEqualOperation(
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}