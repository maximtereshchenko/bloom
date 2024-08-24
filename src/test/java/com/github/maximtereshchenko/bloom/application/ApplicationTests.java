package com.github.maximtereshchenko.bloom.application;

import static org.approvaltests.Approvals.verify;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.maximtereshchenko.bloom.ApprovalsOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * <ul>
 *     <li>
 *         1-chip8-logo - this first test can tell you if you're interpreting these opcodes properly:
 *         <ul>
 *             <li>00E0 - Clear the screen</li>
 *             <li>6xnn - Load normal register with immediate value</li>
 *             <li>Annn - Load index register with immediate value</li>
 *             <li>Dxyn - Draw sprite to screen (only aligned)</li>
 *             <li>1nnn - Jump</li>
 *         </ul>
 *     </li>
 *     <li>
 *         2-ibm-logo - if you can see the IBM logo, you are properly interpreting these opcodes:
 *         <ul>
 *             <li>00E0 - Clear the screen</li>
 *             <li>6xnn - Load normal register with immediate value</li>
 *             <li>Annn - Load index register with immediate value</li>
 *             <li>7xnn - Add immediate value to normal register</li>
 *             <li>Dxyn - Draw sprite to screen (un-aligned)</li>
 *             <li>1nnn - Jump</li>
 *         </ul>
 *     </li>
 *     <li>
 *         3-opcodes - the codes on the screen correspond to the functioning of these opcodes:
 *         <table>
 *             <tr>
 *                 <td>3xnn</td>
 *                 <td>2nnn</td>
 *                 <td>8xy4</td>
 *                 <td>Fx55</td>
 *             </tr>
 *             <tr>
 *                 <td>4xnn</td>
 *                 <td>00EE</td>
 *                 <td>8xy5</td>
 *                 <td>Fx33</td>
 *             </tr>
 *             <tr>
 *                 <td>5xy0</td>
 *                 <td>8xy0</td>
 *                 <td>8xy7</td>
 *                 <td>Fx1E</td>
 *             </tr>
 *             <tr>
 *                 <td>7xnn</td>
 *                 <td>8xy1</td>
 *                 <td>8xy6</td>
 *                 <td>Registers*</td>
 *             </tr>
 *             <tr>
 *                 <td>9xy0</td>
 *                 <td>8xy2</td>
 *                 <td>8xyE</td>
 *             </tr>
 *             <tr>
 *                 <td>1nnn</td>
 *                 <td>8xy3</td>
 *                 <td>Fx65</td>
 *             </tr>
 *         </table>
 *         *The v0 - vF registers should be 8 bits wide. This tests to see if it can overflow your registers.
 *     </li>
 *     <li>
 *         4-flags - This test checks to see if your math operations function properly on some given set of input values.
 *         But more importantly: it checks to see if you set the flag register vF properly when running those opcodes,
 *         and if you don't mess up vF too early (when vF is used as one of the operands).
 *         This is often an issue as the flags are pretty unintuitive and fairly hard to debug.
 *         Each code on the screen corresponds to an opcode, and shows if the output value is correct (first checkmark),
 *         if the flag in vF is correct (second checkmark), if vF can be used as the vY input (third checkmark)
 *         and if vF can be used as the vX input (fourth checkmark, where present).
 *         The top part checks the behaviour of the following opcodes, in the case where we don't expect an overflow,
 *         carry or shifted out bit:
 *         <table>
 *             <tr>
 *                 <td>HAPPY</td>
 *                 <td>8xy1</td>
 *                 <td>8xy2</td>
 *             </tr>
 *             <tr>
 *                 <td>8xy3</td>
 *                 <td>8xy4</td>
 *                 <td>8xy5</td>
 *             </tr>
 *             <tr>
 *                 <td>8xy6</td>
 *                 <td>8xy7</td>
 *                 <td>8xyE</td>
 *             </tr>
 *         </table>
 *         The bottom part checks behaviour of the following opcodes, in the case that there is an overflow,
 *         carry or shifted out bit:
 *         <table>
 *             <tr>
 *                 <td>CARRY</td>
 *                 <td>8xy4</td>
 *                 <td>8xy5</td>
 *             </tr>
 *             <tr>
 *                 <td>8xy6</td>
 *                 <td>8xy7</td>
 *                 <td>8xyE</td>
 *             </tr>
 *         </table>
 *         The last row checks that the opcode Fx1E (i += vX) properly adds the value of register vX to the index
 *         register, first for a regular register and then when using vF as the vX input register.
 *     </li>
 * </ul>
 */
final class ApplicationTests {

    private static final String MOVE_CURSOR_34_LINES_UP = "\033[34F";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @ParameterizedTest
    @ValueSource(strings = {"1-chip8-logo.ch8", "2-ibm-logo.ch8", "3-opcodes.ch8", "4-flags.ch8"})
    void givenProgram_thenProgramExecutedSuccessfully(String program) throws Exception {
        try (var application = application(program)) {
            application.start();

            await().atMost(Duration.ofSeconds(2)).untilAsserted(() -> {
                var output = outputStream.toString(StandardCharsets.UTF_8);
                var start = output.lastIndexOf(MOVE_CURSOR_34_LINES_UP);
                assertNotEquals(-1, start);
                verify(
                    output.substring(start + MOVE_CURSOR_34_LINES_UP.length()),
                    ApprovalsOptions.withParameter(program)
                );
            });
        }
    }

    private Application application(String program) throws URISyntaxException, IOException {
        return Application.from(
            new PrintStream(outputStream),
            Path.of(
                Objects.requireNonNull(
                        Thread.currentThread()
                            .getContextClassLoader()
                            .getResource(program)
                    )
                    .toURI()
            )
        );
    }
}
