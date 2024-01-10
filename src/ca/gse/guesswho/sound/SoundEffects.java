package ca.gse.guesswho.sound;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffects {
	private static final String[] CLIP_LIST = {
			"sad-trombone.wav", "winning.wav"
	};

	private static Map<String, Clip> clipBank = new HashMap<>();

	/**
	 * Loads clips
	 * 
	 * @throws LineUnavailableException is an exception indicating that a line cannot be opened because it is unavailable. This situation arises most commonly when a requested line is already in use by another application.
	 * @throws UnsupportedAudioFileException is an exception indicating that an operation failed because a file did not contain valid data of a recognized file type and format.
	 * @throws IOException is an exceptions produced by failed or interrupted I/O operations.
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
	 * Unloads clips
	 * 
	 */
	public static void unloadClips() {
		for (Clip clip : clipBank.values()) {
			clip.close();
		}
	}


	/**
	 * Loads clips based on the name
	 * @param fileName the name of the file
	 * @return the clip itself.
	 */
	public static Clip getClip(String fileName) {
		return clipBank.get(fileName);
	}
}