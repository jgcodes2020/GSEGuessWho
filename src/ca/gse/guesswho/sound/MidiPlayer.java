package ca.gse.guesswho.sound;

import java.io.Closeable;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
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
	private static final String[] SEQUENCE_LIST = {
		"jeopardy.mid",
	};
	
	private static Map<String, Sequence> sequences = new HashMap<>();
	
	/**
	 * Loads MIDI sequences in this class's preload list.
	 * @throws InvalidMidiDataException if an invalid MIDI file is received
	 * @throws IOException if an I/O error occurs
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
	 * @param name the name of the preloaded sequence
	 * @return the sequence
	 */
	public static Sequence getSequence(String name) {
		return sequences.get(name);
	}
	
	private Sequencer sequencer;
	private Synthesizer synthesizer;
	
	private Transmitter transmitter;
	private Receiver receiver;
	
	private int volume;
	
	public MidiPlayer() throws MidiUnavailableException {
		// this reads out the MIDI notes
		sequencer = MidiSystem.getSequencer();
		// this plays the MIDI notes
		synthesizer = MidiSystem.getSynthesizer();
		// open them
		sequencer.open();
		synthesizer.open();
		// connect the two via a virtual MIDI cable
		transmitter = sequencer.getTransmitter();
		receiver = synthesizer.getReceiver();
		transmitter.setReceiver(receiver);
	}
	/**
	 * Starts playing a sequence, optionally looping it forever.
	 * @param sequence the starting point of the sequence
	 * @param loop if true, loops this sequence.
	 */
	public void playSequence(Sequence sequence, boolean loop) {
		if (sequencer.isRunning())
			sequencer.stop();
			
		try {
			sequencer.setSequence(sequence);
		}
		catch (InvalidMidiDataException exc) {
			// this shouldn't happen.
			throw new IOError(exc);
		}
		
		if (loop) {
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.setLoopStartPoint(0);
			sequencer.setLoopEndPoint(-1);
		}
		else {
			sequencer.setLoopCount(0);
		}
		sequencer.setTickPosition(0);
		sequencer.start();
	}
	
	/**
	 * Starts playing a pre-loaded sequence, optionally looping it forever.
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
	 * 
	 * @param value
	 */
	public void setVolume(int value) {
		if (volume < 0 || volume > 127) 
			throw new IllegalArgumentException("Invalid MIDI volume");
		
		MidiChannel[] channels = synthesizer.getChannels();
		
	}
	
	/**
	 * Closes all MIDI objects owned by the MidiManager.
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public void close() throws IOException {
		sequencer.stop();
		
		transmitter.close();
		receiver.close();
		sequencer.close();
		synthesizer.close();
	}
	
	
}
