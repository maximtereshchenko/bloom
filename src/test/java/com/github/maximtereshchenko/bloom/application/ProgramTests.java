package com.github.maximtereshchenko.bloom.application;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

final class ProgramTests {

    @Test
    void givenSetIndexRegisterValue_thenValueFromMemoryAddressDisplayed() throws URISyntaxException, IOException {
        new Dsl()
            .givenProgram("ibm-logo.ch8", 100)
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }
}
