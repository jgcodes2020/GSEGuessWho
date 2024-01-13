/*
Utilities.java
Authors: Jacky Guo
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility functions that don't exist in the Java standard library.
 */
public class Utilities {
    // This is a regular expression. Regular expressions are very powerful tools for
    // parsing strings,
    // so this makes my life a bit easier. See note 2 in README for explanation.
    private static final Pattern TIME_REGEX = Pattern
            .compile("^(?:(\\d|[1-9]\\d+)d\\+)?(\\d{2}):(\\d{2}):(\\d{2})(?:\\.(\\d{3}))?$");

    private static Random rng = new Random();

    /**
     * Shuffles an array of integers.
     * 
     * @param array the array of integers
     */
    public static void shuffle(int[] array) {
        // Standard Fisher-Yates shuffle algorithm. Pick one element, swaps it to the
        // front.
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
     * 
     * @param array the array
     * @return a random element of the array
     */
    public static <T> T pickRandom(T[] array) {
        return array[rng.nextInt(array.length)];
    }

    /**
     * Sorts a list by the specified comparator.
     * 
     * @param <T>  the type being sorted
     * @param list the list to sort
     * @param cmp  the comparator to order elements by
     */
    public static <T> void sort(List<T> list, Comparator<T> cmp) {
        // this is just insertion sort for now, it should be fairly efficient for small
        // lists.
        // Insertion sort works by repeatedly adding elements to a sorted list.
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0 && cmp.compare(list.get(j - 1), list.get(j)) > 0; j++) {
                T temp = list.get(j - 1);
                list.set(j - 1, list.get(j));
                list.set(j, temp);
            }
        }
    }

    /**
     * Sorts a list according to the element's natural order
     * 
     * @param <T>  the type being sorted, it must be comparable to itself.
     * @param list the list to sort
     */
    public static <T extends Comparable<T>> void sort(List<T> list) {
        sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                // use the default comparison between the two objects
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Inserts an element into a sorted list according to a comparator.
     * 
     * @param <T>  the type being sorted
     * @param list the list to insert
     * @param item the item to insert
     * @param cmp  the comparator to use
	 * @return the index where the item is after insertion
     */
    public static <T> int insertIntoSorted(List<T> list, T item, Comparator<T> cmp) {
        // this works like a single pass of insertion sort: put the item at the end
        // and move it to the left until it is greater than the item to its left.
        list.add(item);
		int i;
        for (i = list.size() - 1; i > 0 && cmp.compare(list.get(i - 1), list.get(i)) > 0; i--) {
            T temp = list.get(i - 1);
            list.set(i - 1, list.get(i));
            list.set(i, temp);
        }
		return i;
    }

    public static <T extends Comparable<T>> void insertIntoSorted(List<T> list, T item) {
        insertIntoSorted(list, item, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                // use the default comparison between the two objects
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Converts a number of milliseconds to a more readable time format.
     * 
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
        String result = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
        // add days (if needed)
        if (days > 0)
            result = String.format("%dd+%s", days, result);
        // example: 2d+16:32:59.231
        return result;
    }

    /**
     * Converts a string formatted by {@link Utilities#millisToString(long)} back
     * to a number of milliseconds.
     * 
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
