package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.Random;

/**
 * Utility functions that don't exist in the Java standard library.
 */
public class Utilities {
	private static Random rng = new Random();
	
	/**
	 * Shuffles an array of integers.
	 * @param array the array of integers
	 */
	public static void shuffle(int[] array) {
		// Standard Fisher-Yates shuffle algorithm. Pick one element, swaps it to the front.
		// Do the same for the remaining elements.
		for (int i = 0; i < array.length - 1; i++) {
			int swapIndex = i + rng.nextInt(array.length - i);
			
			int temp = array[i];
			array[i] = array[swapIndex];
			array[swapIndex] = temp;
		}
	}
	
	/**
	 * Copies the bits of one bitset to another
	 * @param src
	 * @param dst
	 */
	public static void copy(BitSet src, BitSet dst) {
		dst.clear();
		dst.or(src);
	}
}
