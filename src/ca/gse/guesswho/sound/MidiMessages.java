/*
MidiMessages.java
Authors: Jacky Guo
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

/**
 * Utility functions for generating MIDI messages.
 */
public class MidiMessages {
	private MidiMessages() {}
	
	/**
	 * Returns a MIDI message to set the master volume of the
	 * synthesizer.
	 * @param volume the volume to set, between 0 and 16383. The value will be clamped as needed.
	 * @return a message that sets the volume.
	 */
	public static MidiMessage masterVolume(int volume) {
		volume = Math.min(Math.max(volume, 0), 16383);
		
		// format derived from here
		// http://midi.teragonaudio.com/tech/midispec/mastrvol.htm
		byte[] data = new byte[] {
			// header (Universal SysEx message; channel unspecified; device control; master volume)
			(byte) 0x7F, (byte) 0x7F, (byte) 0x04, (byte) 0x01,
			// 14 bits of volume, low bits first then high bits
			(byte) (volume & 0x7F), (byte) ((volume >> 7) & 0x7F)
		};
		try {
			// creat the MIDI message
			SysexMessage msg = new SysexMessage(0xF0, data, data.length);
			return msg;
		} catch (InvalidMidiDataException exc) {
			throw new Error("MIDI broke", exc);
		}
	}
}
