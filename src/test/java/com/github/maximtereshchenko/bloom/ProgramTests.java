package com.github.maximtereshchenko.bloom;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

final class ProgramTests {

    @Test
    void givenIbmLogoProgram_thenIbmLogoDisplayed() throws URISyntaxException, IOException {
        new Dsl()
            .givenProgram("ibm-logo.ch8")
            .whenExecuteInstructions(40)
            .thenOutputMatchesExpectation();
    }
}
