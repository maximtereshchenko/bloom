package com.github.maximtereshchenko.bloom.domain;

final class SetFontCharacterOperationFactory extends SimpleOperationFactory {

    @Override
    boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.F &&
            operationCode.middleRightNibble() == HexadecimalSymbol.TWO
            && operationCode.lastNibble() == HexadecimalSymbol.NINE;
    }

    @Override
    Operation supportedOperation(OperationCode operationCode) {
        return new SetFontCharacterOperation(operationCode.middleLeftNibble());
    }
}
