package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValuesNotEqualOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.NINE &&
            operationCode.nibble(3) == HexadecimalSymbol.ZERO;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new SkipIfRegisterValuesNotEqualOperation(operationCode.nibble(1), operationCode.nibble(2));
    }
}
