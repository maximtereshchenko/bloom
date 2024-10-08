package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.ApprovalsOptions;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.approvaltests.Approvals;
import org.approvaltests.writers.ComponentApprovalWriter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sound.sampled.LineEvent.Type;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <ul>
 *     <li>
 *         1-chip8-logo - this first test can tell you if you're interpreting these opcodes
 *         properly:
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
 *         *The v0 - vF registers should be 8 bits wide. This tests to see if it can overflow
 *         your registers.
 *     </li>
 *     <li>
 *         4-flags - This test checks to see if your math operations function properly on some
 *         given set of input values. But more importantly: it checks to see if you set the flag
 *         register vF properly when running those opcodes, and if you don't mess up vF too early
 *         (when vF is used as one of the operands). This is often an issue as the flags are
 *         pretty unintuitive and fairly hard to debug. Each code on the screen corresponds to an
 *         opcode, and shows if the output value is correct (first checkmark), if the flag in vF
 *         is correct (second checkmark), if vF can be used as the vY input (third checkmark) and
 *         if vF can be used as the vX input (fourth checkmark, where present). The top part
 *         checks the behaviour of the following opcodes, in the case where we don't expect an
 *         overflow, carry or shifted out bit:
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
 *         The bottom part checks behaviour of the following opcodes, in the case that there is
 *         an overflow, carry or shifted out bit:
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
 *         The last row checks that the opcode Fx1E (i += vX) properly adds the value of register
 *         vX to the index register, first for a regular register and then when using vF as the
 *         vX input register.
 *     </li>
 *     <li>
 *         5-quirks - The screen shows you if the following quirks are detected as active ("on",
 *         "off" or an error) and if that matches your chosen target platform (a checkmark or a
 *         cross).
 *         <ul>
 *             <li>
 *                 vF reset - The AND, OR and XOR opcodes (8xy1, 8xy2 and 8xy3) reset the flags
 *                 register to zero. Test will show ERR1 if the AND and OR tests don't behave the
 *                 same and ERR2 if the AND and XOR tests don't behave the same.
 *             </li>
 *             <li>
 *                 Memory - The save and load opcodes (Fx55 and Fx65) increment the index
 *                 register. Test will show ERR1 if reading and writing don't behave the same.
 *             </li>
 *             <li>
 *                 Display wait - Drawing sprites to the display waits for the vertical blank
 *                 interrupt, limiting their speed to max 60 sprites per second.
 *             </li>
 *             <li>
 *                 Clipping - Sprites drawn at the bottom edge of the screen get clipped instead
 *                 of wrapping around to the top of the screen. This also tests that sprites
 *                 drawn at coordinates of x > 63 and/or y > 31 wrap around to x % 64 and y % 32.
 *             </li>
 *             <li>
 *                 Shifting - The shift opcodes (8xy6 and 8xyE) only operate on vX instead of
 *                 storing the shifted version of vY in vX.
 *             </li>
 *             <li>
 *                 Jumping - The "jump to some address plus v0" instruction (Bnnn) doesn't use
 *                 v0, but vX instead where X is the highest nibble of nnn.
 *             </li>
 *         </ul>
 *         Note that you need timer support for this test to run.
 *     </li>
 *     <li>
 *         6-keypad- this test allows you to test all three CHIP-8 key input opcodes.
 *         <ul>
 *             <li>
 *                 Ex9E DOWN - in the test, when you press a key, the corresponding value lights
 *                 up on the screen.
 *             </li>
 *             <li>
 *                 ExA1 UP - in the test, when you are not pressing a key, the corresponding
 *                 value lights up
 *                 on the screen.
 *             </li>
 *             <li>
 *                 Fx0A GETKEY - the test asks you to press a key on the CHIP-8 keypad. When you do,
 *                 it checks for two issues that are easy to accidentally introduce when
 *                 implementing this opcode.
 *                 If all is well, you should be seeing a checkmark and "all good" on the screen.
 *             </li>
 *         </ul>
 *     </li>
 *     <li>
 *         7-beep - this test allows you to test if your buzzer is working. It will beep SOS
 *         in morse code and flash a speaker icon on the display in the same pattern. If you
 *         press the CHIP-8 button B it will give you manual control over the buzzer. Press B to
 *         beep.
 *     </li>
 * </ul>
 */
@WireMockTest
final class ApplicationTests {

    private static Stream<Arguments> programs() {
        return Stream.of(
            arguments(file("1-chip8-logo.ch8"), List.of(), false),
            arguments(file("2-ibm-logo.ch8"), List.of(), false),
            arguments(file("3-opcodes.ch8"), List.of(), false),
            arguments(file("4-flags.ch8"), List.of(), false),
            arguments(
                file("5-quirks.ch8"),
                List.of(
                    keyEvent(KeyEvent.KEY_PRESSED, '1'),
                    keyEvent(KeyEvent.KEY_RELEASED, '1')
                ),
                false
            ),
            arguments(
                file("6-keypad.ch8"),
                List.of(
                    keyEvent(KeyEvent.KEY_PRESSED, '1'),
                    keyEvent(KeyEvent.KEY_RELEASED, '1'),
                    keyEvent(KeyEvent.KEY_PRESSED, '1')
                ),
                false
            ),
            arguments(
                file("6-keypad.ch8"),
                List.of(
                    keyEvent(KeyEvent.KEY_PRESSED, '2'),
                    keyEvent(KeyEvent.KEY_RELEASED, '2'),
                    keyEvent(KeyEvent.KEY_PRESSED, '1')
                ),
                false
            ),
            arguments(
                file("6-keypad.ch8"),
                List.of(
                    keyEvent(KeyEvent.KEY_PRESSED, '3'),
                    keyEvent(KeyEvent.KEY_RELEASED, '3'),
                    keyEvent(KeyEvent.KEY_PRESSED, '1'),
                    keyEvent(KeyEvent.KEY_RELEASED, '1')
                ),
                false
            ),
            arguments(
                file("7-beep.ch8"),
                List.of(
                    keyEvent(KeyEvent.KEY_PRESSED, 'c')
                ),
                true
            ),
            arguments(
                file("7-beep.ch8"),
                List.of(
                    keyEvent(KeyEvent.KEY_PRESSED, 'c'),
                    keyEvent(KeyEvent.KEY_RELEASED, 'c')
                ),
                false
            ),
            arguments(link("1-chip8-logo.ch8"), List.of(), false)
        );
    }

    private static UnaryOperator<String> file(String name) {
        return baseUrl -> path(name);
    }

    private static String path(String name) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Thread.currentThread()
                                .getContextClassLoader()
                                .getResource(name)
                        )
                        .toURI()
                )
                .toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static UnaryOperator<String> link(String name) {
        return baseUrl -> {
            try {
                stubFor(
                    get("/" + name)
                        .willReturn(ok().withBody(Files.readAllBytes(Paths.get(path(name)))))
                );
                return baseUrl + '/' + name;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    private static Function<Component, KeyEvent> keyEvent(int id, char keyChar) {
        return source -> new KeyEvent(
            source,
            id,
            Instant.now().getEpochSecond(),
            0,
            Character.toUpperCase(keyChar),
            keyChar
        );
    }

    @ParameterizedTest
    @MethodSource("programs")
    void givenProgram_thenProgramExecutedSuccessfully(
        UnaryOperator<String> locationFunction,
        List<Function<Component, KeyEvent>> keys,
        boolean expectedSoundEnabled,
        ArgumentsAccessor argumentsAccessor,
        WireMockRuntimeInfo wireMockRuntimeInfo
    ) throws Exception {
        var location = locationFunction.apply(wireMockRuntimeInfo.getHttpBaseUrl());
        var isSoundEnabled = new AtomicBoolean(false);
        try (
            var application = JFrameApplication.from(
                location,
                event -> isSoundEnabled.set(event.getType() == Type.START)
            )
        ) {
            application.start();
            pressKeys(application, keys);
            await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
                    Approvals.verify(
                        new ComponentApprovalWriter(application.getContentPane()),
                        ApprovalsOptions.defaultConfiguration(
                            location.substring(location.lastIndexOf('/') + 1),
                            String.valueOf(argumentsAccessor.getInvocationIndex())
                        )
                    );
                    assertThat(isSoundEnabled.get()).isEqualTo(expectedSoundEnabled);
                }
            );
        }
    }

    private void pressKeys(
        JFrameApplication application,
        List<Function<Component, KeyEvent>> keys
    ) {
        if (!keys.isEmpty()) {
            SwingUtilities.invokeLater(() -> application.setVisible(true));
        }
        try (var executorService = Executors.newScheduledThreadPool(1)) {
            for (int i = 0; i < keys.size(); i++) {
                var keyEvent = keys.get(i).apply(application);
                executorService.schedule(
                    () -> SwingUtilities.invokeLater(() -> application.dispatchEvent(keyEvent)),
                    i,
                    TimeUnit.SECONDS
                );
            }
        }
    }
}