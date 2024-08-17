package com.github.maximtereshchenko.bloom.domain;

final class SetRegisterValueOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.SIX;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new SetRegisterValueOperation(
            operationCode.nibble(1),
            new Hexadecimal(operationCode.nibble(2), operationCode.nibble(3)).asByte()
        );
    }
}
