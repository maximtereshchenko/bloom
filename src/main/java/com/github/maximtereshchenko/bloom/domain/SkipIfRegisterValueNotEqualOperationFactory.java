package com.github.maximtereshchenko.bloom.domain;

final class SkipIfRegisterValueNotEqualOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.FOUR;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new SkipIfRegisterValueNotEqualOperation(
            operationCode.nibble(1),
            new Hexadecimal(operationCode.nibble(2), operationCode.nibble(3)).asByte()
        );
    }
}
