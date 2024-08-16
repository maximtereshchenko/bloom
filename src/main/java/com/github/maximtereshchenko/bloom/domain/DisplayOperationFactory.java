package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

final class DisplayOperationFactory implements OperationFactory {

    private final Display display;

    DisplayOperationFactory(Display display) {
        this.display = display;
    }

    @Override
    public boolean supports(OperationCode operationCode) {
        return operationCode.nibble(0) == HexadecimalSymbol.D;
    }

    @Override
    public Operation operation(OperationCode operationCode) {
        return new DisplayOperation(
            display,
            operationCode.nibble(1).numericValue(),
            operationCode.nibble(2).numericValue(),
            operationCode.nibble(3).numericValue()
        );
    }
}
