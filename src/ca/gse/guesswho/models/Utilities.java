package ca.gse.guesswho.models;

import java.time.Duration;
import java.util.BitSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility functions that don't exist in the Java standard library.
 */
public class Utilities {
	// This is a regular expression. Regular expressions are very powerful tools for parsing strings,
	// so this makes my life a bit easier. See note 2 in README for explanation.
	private static final Pattern TIME_REGEX = Pattern.compile("^(?:(\\d|[1-9]\\d+)d\\+)?(\\d{2}):(\\d{2}):(\\d{2})(?:\\.(\\d{3}))?$");
	
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
	 * Selects a random element of an array.
	 * @param array the array
	 * @return a random element of the array
	 */
	public static <T> T pickRandom(T[] array) {
		return array[rng.nextInt(array.length)];
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
	
	/**
	 * Converts a number of milliseconds to a more readable time format.
	 * @param t the number of milliseconds
	 * @return a string representing that number of milliseconds
	 */
	public static String millisToString(long t) {
		// compute milliseconds of the second
		long millis = t % 1000;
		t = t / 1000;
		// compute seconds of the minute
		long seconds = t % 60;
		t /= 60;
		// compute minutes of the hour
		long minutes = t % 60;
		t /= 60;
		// compute hours of the day
		long hours = t % 24;
		t /= 24;
		// left over: days
		long days = t;
		// add hours/minutes/seconds
		String result = String.format("%2d:%2d:%2d.%3d", hours, minutes, seconds, millis);
		// add days (if needed)
		if (days > 0)
			result = String.format("%dd+%s", days, result);
		return result;
	}
	
	/**
	 * Converts a string formatted by {@link Utilities#millisToString(long)} back
	 * to a number of milliseconds.
	 * @param s the time string.
	 * @return the number of milliseconds this string corresponds to.
	 */
	public static long stringToMillis(String s) {
		// use the regex to extract relevant features
		Matcher match = TIME_REGEX.matcher(s);
		if (!match.matches())
			throw new IllegalArgumentException("Invalid time format");
		String daysStr = match.group(1);
		String hoursStr = match.group(2);
		String minsStr = match.group(3);
		String secsStr = match.group(4);
		String millisStr = match.group(5);
		
		long time = 0;
		// add days (optional) (1 d = 86 400 s = 86 400 000 ms)
		if (daysStr != null)
			time += Integer.parseInt(daysStr) * 86400000;
		// add hours (1 h = 3600 s = 3 600 000 ms)
		time += Integer.parseInt(hoursStr) * 3600000;
		// add minutes (1 min = 60 s = 60 000 ms)
		time += Integer.parseInt(minsStr) * 60000;
		// add seconds (1 s = 1000 ms)
		time += Integer.parseInt(secsStr) * 1000;
		// add milliseconds (optional)
		if (millisStr != null)
			time += Integer.parseInt(millisStr);
		
		return time;
	}
}
