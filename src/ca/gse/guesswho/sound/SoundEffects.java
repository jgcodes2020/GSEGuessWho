/*
SoundEffects.java
Authors: Jacky Guo
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.sound;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffects {
    /**
     * A list of clips by filename inside the source tree.
     */
    private static final String[] CLIP_LIST = {
            "sad-trombone.wav",
            "winning.wav",
            "ding.wav"
    };

    private static Map<String, Clip> clipBank = new HashMap<>();
    private static double volume = 0.5;

    /**
     * Loads clips in the preload list.
     * 
     * @throws LineUnavailableException      is an exception indicating that a line
     *                                       cannot be opened because it is
     *                                       unavailable. This situation arises most
     *                                       commonly when a requested line is
     *                                       already in use by another application.
     * @throws UnsupportedAudioFileException is an exception indicating that an
     *                                       operation failed because a file did not
     *                                       contain valid data of a recognized file
     *                                       type and format.
     * @throws IOException                   is an exceptions produced by failed or
     *                                       interrupted I/O operations.
     * @see SoundEffects#CLIP_LIST
     */
    public static void loadClips() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        for (String clipName : CLIP_LIST) {
            URL url = SoundEffects.class.getResource("/ca/gse/guesswho/assets/sfx/" + clipName);
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            clip.open(stream);
            clipBank.put(clipName, clip);
        }
    }

    /**
     * Unloads clips. This should preferably be called prior to killing the
     * application.
     */
    public static void unloadClips() {
        for (Clip clip : clipBank.values()) {
            clip.close();
        }
    }

    /**
     * Plays a preloaded clip.
     * 
     * @param fileName the name of the file
     */
    public static void playClip(String fileName) {
        Clip c = clipBank.get(fileName);

        // We have to invert the equation provided here; converting a multiplier to
        // decibels.
        // https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/FloatControl.Type.html#MASTER_GAIN
        FloatControl volumeCtrl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        BooleanControl muteCtrl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);

        // -60 dB -> * 10^(-30). -60 dB is equivalent to muting.
        if (volume <= 1e-30) {
            muteCtrl.setValue(true);
        } else {
            muteCtrl.setValue(false);
            volumeCtrl.setValue((float) (20.0 * Math.log10(volume)));
        }
        // This should restart and play the sound. If you try to restart the clip
        // very quickly in short intervals, it seems to mess up. This is a known bug
        // and I have no idea how to fix it.
        c.stop();
        c.flush();
        c.setFramePosition(0);
        c.start();
    }

    /**
     * Gets the volume used for sound effects.
     * 
     * @return the volume used for sound effects, where 0.0 is muted and 1.0 is full
     *         volume
     */
    public static double getVolume() {
        return volume;
    }

    /**
     * Sets the volume for sound effects.
     * 
     * @param volume the volume, where 0.0 is muted and 1.0 is full volume
     */
    public static void setVolume(double volume) {
        if (volume < 0.0 || volume > 1.0)
            throw new IllegalArgumentException("Volume must be betwee 0.0 and 1.0");
        SoundEffects.volume = volume;
    }
}