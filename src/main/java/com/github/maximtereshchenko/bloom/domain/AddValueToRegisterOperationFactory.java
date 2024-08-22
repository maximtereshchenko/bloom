package com.github.maximtereshchenko.bloom.domain;

final class AddValueToRegisterOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.SEVEN;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new AddValueToRegisterOperation(
            operationCode.nibble(1),
            new Hexadecimal(operationCode.nibble(2), operationCode.nibble(3)).asByte()
        );
    }
}
