package com.github.maximtereshchenko.bloom.domain;

final class ClearDisplayOperationFactory implements OperationFactory {

    private final StagingDisplay display;

    ClearDisplayOperationFactory(StagingDisplay display) {
        this.display = display;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.firstNibble() == HexadecimalSymbol.ZERO &&
               operationCode.middleLeftNibble() == HexadecimalSymbol.ZERO &&
               operationCode.middleRightNibble() == HexadecimalSymbol.E &&
               operationCode.lastNibble() == HexadecimalSymbol.ZERO;
    }

    @Override
    public Operation supportedOperation(OperationCode operationCode) {
        return new ClearDisplayOperation(display);
    }
}
