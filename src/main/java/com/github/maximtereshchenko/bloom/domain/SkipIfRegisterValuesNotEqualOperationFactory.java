package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesNotEqualOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.NINE &&
            operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfRegisterValuesNotEqualOperation(
            operationCode.middleLeftNibble(),
            operationCode.middleRightNibble()
        );
    }
}