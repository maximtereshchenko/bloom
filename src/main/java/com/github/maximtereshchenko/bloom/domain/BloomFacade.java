package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import java.util.Set;

public final class BloomFacade implements BloomModule {

    private final RandomAccessMemory randomAccessMemory;
    private final Registers registers;
    private final Display display;
    private final Set<OperationFactory> operationFactories;

    public BloomFacade(byte[] program) {
        this.randomAccessMemory = RandomAccessMemory.withProgram(program);
        this.registers = new Registers();
        this.display = new Display();
        this.operationFactories = Set.of(
            new SetFontCharacterOperationFactory(),
            new DisplayOperationFactory(),
            new SetRegisterValueOperationFactory(),
            new ClearDisplayOperationFactory(),
            new JumpOperationFactory(),
            new AddValueToRegisterOperationFactory(),
            new SetIndexRegisterValueOperationFactory()
        );
    }

    @Override
    public void executeNextInstruction() {
        var operationCode = operationCode();
        for (var operationFactory : operationFactories) {
            if (operationFactory.supports(operationCode)) {
                operationFactory.operation(operationCode)
                    .execute(registers, randomAccessMemory, display);
                return;
            }
        }
        throw new IllegalArgumentException(operationCode.toString());
    }

    @Override
    public boolean[][] displayMask() {
        return display.mask();
    }

    private OperationCode operationCode() {
        var first = registers.programCounter().value();
        var second = first.next();
        var operationCode = new OperationCode(randomAccessMemory.value(first), randomAccessMemory.value(second));
        registers.programCounter().set(second.next());
        return operationCode;
    }
}
