/*
MidiPlayer.java
Authors: Jacky Guo
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.sound;

import java.io.Closeable;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 * Class managing various MIDI resources needed for playing MIDI files.
 */
public class MidiPlayer implements Closeable {
	/**
	 * List of MIDI files to preload.
	 */
	private static final String[] SEQUENCE_LIST = {
			"jeopardy.mid",
			"guesswho.mid"
	};

	private static Map<String, Sequence> sequences = new HashMap<>();

	/**
	 * Loads MIDI sequences in this class's preload list.
	 * 
	 * @throws InvalidMidiDataException if an invalid MIDI file is received
	 * @throws IOException              if an I/O error occurs
	 * @see MidiPlayer#SEQUENCE_LIST
	 */
	public static void loadSequences() throws InvalidMidiDataException, IOException {
		for (String seqName : SEQUENCE_LIST) {
			URL url = SoundEffects.class.getResource("/ca/gse/guesswho/assets/music/" + seqName);
			Sequence sequence = MidiSystem.getSequence(url);
			sequences.put(seqName, sequence);
		}
	}

	/**
	 * Gets a pre-loaded sequence.
	 * 
	 * @param name the name of the preloaded sequence
	 * @return the sequence
	 */
	public static Sequence getSequence(String name) {
		return sequences.get(name);
	}

	private Sequencer sequencer;
	private Synthesizer synthesizer;

	private Transmitter sequenceTransmitter;
	private Receiver sequenceReceiver;
	private Receiver controlReceiver;

	private int volume;

	/**
	 * Creates a new MIDI player, allocating all needed resources to do so.
	 * 
	 * @throws MidiUnavailableException If the system does not support MIDI
	 *                                  playback.
	 */
	public MidiPlayer() throws MidiUnavailableException {
		// sequencer reads MIDI notes (extra parameter needed to make it not do extra
		// stuff we don't want)
		sequencer = MidiSystem.getSequencer(false);
		// synthesizer plays MIDI notes
		synthesizer = MidiSystem.getSynthesizer();
		// open them
		sequencer.open();
		synthesizer.open();
		// setup connection ports
		sequenceTransmitter = sequencer.getTransmitter();
		sequenceReceiver = synthesizer.getReceiver();
		controlReceiver = synthesizer.getReceiver();
		// connect sequence transmitter and sequence receiver
		sequenceTransmitter.setReceiver(sequenceReceiver);

		// set the volume to 50% for now
		setVolume(0.5);
	}

	/**
	 * Starts playing a sequence, optionally looping it forever.
	 * 
	 * @param sequence the starting point of the sequence
	 * @param loop     if true, loops this sequence.
	 */
	public void playSequence(Sequence sequence, boolean loop) {
		if (sequencer.isRunning())
			sequencer.stop();

		// set the MIDI sequence to the one we want to play
		try {
			sequencer.setSequence(sequence);
		} catch (InvalidMidiDataException exc) {
			// this shouldn't happen.
			throw new IOError(exc);
		}

		if (loop) {
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.setLoopStartPoint(0);
			sequencer.setLoopEndPoint(-1);
		} else {
			sequencer.setLoopCount(0);
		}
		sequencer.setTickPosition(0);
		sequencer.start();
	}

	/**
	 * Starts playing a pre-loaded sequence, optionally looping it forever.
	 * 
	 * @param name the name of the pre-loaded sequence
	 * @param loop if true, loops this sequence.
	 */
	public void playLoadedSequence(String name, boolean loop) {
		playSequence(getSequence(name), loop);
	}

	/**
	 * Stops any currently playing music.
	 */
	public void stop() {
		sequencer.stop();
	}

	/**
	 * Sets the volume. The provided parameter will be clamped inside the method.
	 * 
	 * @param value The volume, where 0 is muted and 1 is full volume
	 */
	public void setVolume(double value) {
		value = Math.min(Math.max(value, 0.0), 1.0);
		// MIDI volume is a 14-bit number from 0 to 16383
		this.volume = (int) (value * 16383);
		// tell the system to lower master volume
		controlReceiver.send(MidiMessages.masterVolume(volume), -1);
	}

	/**
	 * Gets the volume.
	 * 
	 * @return The volume, where 0 is muted and 1 is full volume
	 */
	public double getVolume() {
		return (double) this.volume / 16383;
	}

	/**
	 * Closes all MIDI objects owned by the MidiManager.
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public void close() throws IOException {
		// stop the music
		sequencer.stop();
		// close everythin
		sequenceTransmitter.close();
		sequenceReceiver.close();
		sequencer.close();
		synthesizer.close();
	}

}
