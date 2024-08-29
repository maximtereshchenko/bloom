package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.port.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

final class ClipSound implements Sound {

    private final Clip clip;

    private ClipSound(Clip clip) {
        this.clip = clip;
    }

    static Sound configured(LineListener lineListener) throws Exception {
        var audioStream = AudioSystem.getAudioInputStream(
            new BufferedInputStream(
                Files.newInputStream(
                    Paths.get(
                        Objects.requireNonNull(
                                Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResource("beep.wave")
                            )
                            .toURI()
                    )
                )
            )
        );
        var clip = (Clip) AudioSystem.getLine(
            new DataLine.Info(Clip.class, audioStream.getFormat(), audioStream.available())
        );
        clip.open(audioStream);
        clip.addLineListener(lineListener);
        return new ClipSound(clip);
    }

    @Override
    public void enable() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void disable() {
        clip.stop();
    }
}
