package ca.gse.guesswho.sound;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffects {
	private static final String[] CLIP_LIST = {
			"sad-trombone.wav"
	};

	private static Map<String, Clip> clipBank;

	public static void loadClips() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		for (String clipName : CLIP_LIST) {
			URL url = SoundEffects.class.getResource("/ca/gse/guesswho/assets/sfx/" + clipName);
			Clip clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);

			clip.open(stream);
			clipBank.put(clipName, clip);
		}
	}

	public static void unloadClips() {
		for (Clip clip : clipBank.values()) {
			clip.close();
		}
	}

	public static Clip getClip(String fileName) {
		return clipBank.get(fileName);
	}
}