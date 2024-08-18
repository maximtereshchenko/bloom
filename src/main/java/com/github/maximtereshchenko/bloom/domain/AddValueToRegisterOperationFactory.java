package com.github.maximtereshchenko.bloom.domain;

final class AddValueToRegisterOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.SEVEN;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new AddValueToRegisterOperation(
            operationCode.nibble(1),
            new Hexadecimal(operationCode.nibble(2), operationCode.nibble(3)).asByte()
        );
    }
}
