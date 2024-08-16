package com.github.maximtereshchenko.bloom.domain;

final class SetFontCharacterOperationFactory implements OperationFactory {

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.F &&
            operationCode.nibble(2) == HexadecimalSymbol.TWO
            && operationCode.nibble(3) == HexadecimalSymbol.NINE;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new SetFontCharacterOperation(operationCode.nibble(1));
    }
}
