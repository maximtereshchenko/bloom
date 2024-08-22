package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesEqualOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.FIVE &&
            operationCode.nibble(3) == HexadecimalSymbol.ZERO;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SkipIfRegisterValuesEqualOperation(operationCode.nibble(1), operationCode.nibble(2));
    }
}
