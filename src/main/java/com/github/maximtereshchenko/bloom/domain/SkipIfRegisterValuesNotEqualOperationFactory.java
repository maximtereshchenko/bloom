package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesNotEqualOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.NINE &&
            operationCode.nibble(3) == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfRegisterValuesNotEqualOperation(operationCode.nibble(1), operationCode.nibble(2));
    }
}
