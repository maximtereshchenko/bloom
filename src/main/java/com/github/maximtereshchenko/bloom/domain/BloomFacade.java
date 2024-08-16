package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.api.Display;
import java.util.Set;

public final class BloomFacade implements BloomModule {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final Set<OperationFactory> operationFactories;

    public BloomFacade(byte[] program, Display display) {
        this.registers = new Registers(new ProgramCounter(), new Index());
        this.randomAccessMemory = RandomAccessMemory.withProgram(program);
        this.operationFactories = Set.of(
            new SetFontCharacterOperationFactory(),
            new DisplayOperationFactory(display)
        );
    }

    @Override
    public void executeNextInstruction() {
        var operationCode = operationCode();
        for (var operationFactory : operationFactories) {
            if (operationFactory.supports(operationCode)) {
                operationFactory.operation(operationCode)
                    .execute(registers, randomAccessMemory);
                break;
            }
        }
    }

    private OperationCode operationCode() {
        var first = registers.programCounter().value();
        var second = first.next();
        var operationCode = new OperationCode(randomAccessMemory.value(first), randomAccessMemory.value(second));
        registers.programCounter().set(second.next());
        return operationCode;
    }
}
