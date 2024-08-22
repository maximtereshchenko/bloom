package com.github.maximtereshchenko.bloom.domain;

final class SetFontCharacterOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.F &&
            operationCode.nibble(2) == HexadecimalSymbol.TWO
            && operationCode.nibble(3) == HexadecimalSymbol.NINE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SetFontCharacterOperation(operationCode.nibble(1));
    }
}
