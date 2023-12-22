package ca.gse.guesswho.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

import ca.gse.guesswho.models.questions.AttributeQuestion;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class handling all data that is loaded from files.
 */
public class DataCaches {
	private static List<GuessWhoCharacter> characterList = null;
	private static Map<String, AttributeQuestion> questionBank = null;
	
	/**
	 * Loads and parses the character list from a CSV file. This must be done before
	 * creating any {@link GameState} instances.
	 * 
	 * @param path the path to load from.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void loadCharacters(URL path) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new ProgressMonitorInputStream(null, "Loading game...", path.openStream())))) {
			// this stores the last line we read
			String line;
			// this keeps track of the characters we've parsed
			ArrayList<GuessWhoCharacter> characters = new ArrayList<>();
			// read the header row. We could validate it, but I'm not doing that now.
			line = br.readLine();
			if (line == null)
				throw new IllegalArgumentException("Parsing failed! (missing header row)");
			// read a line, if it isn't the end of the file, parse and add it
			// rinse and repeat until br.readLine() returns null (i.e. end of file)
			while ((line = br.readLine()) != null) {
				characters.add(GuessWhoCharacter.fromCsvRow(line));
			}
			// convert the character list to an unmodifiable list.
			characterList = Collections.unmodifiableList(characters);
		}
	}

	/**
	 * Gets the global character list.
	 * 
	 * @return the global character list, or null if it hasn't been loaded
	 */
	public static List<GuessWhoCharacter> getCharacterList() {
		return characterList;
	}
	
	public static GuessWhoCharacter getCharacterByName(String name) {
		if (characterList == null)
			throw new IllegalStateException("Character list isn't loaded yet!");
		
		// Iterator-based iteration. This is what the 'for (Type thing : list)' effectively does
		// on the inside; 
		for (GuessWhoCharacter gwCharacter : characterList) {
			if (gwCharacter.getName().equals(name))
				return gwCharacter;
		}
		throw new IllegalArgumentException("There is no characted named " + name);
	}
	
	/**
	 * Loads and parses the question bank from a CSV file.
	 * @param path the path to load from.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void loadQuestions(URL source) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()))) {
			String line;
			// This works a lot like HashMap, but unlike HashMap, it maintains the order
			// of elements when iterating. This means that questions appear in the exact
			// order they are stored in the question bank.
			LinkedHashMap<String, AttributeQuestion> map = new LinkedHashMap<>();
            // read the header row. We could validate it, but I'm not doing that now.
            line = br.readLine();
            if (line == null)
                throw new IllegalArgumentException("Parsing failed! (missing header row)");
			// read each data row, converting it to a key and attribute question.
			while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
				// columns 1 and 2 contain an attribute and value
				AttributeQuestion question = new AttributeQuestion(Integer.parseInt(parts[1]), Byte.parseByte(parts[2]));
				// column 0 contains the question text
				map.put(parts[0], question);
            }
			
			// store it as an immutable map; we don't want the question bank to change after loading
			questionBank = Collections.unmodifiableMap(map);
		}
	}
	
	/**
	 * Gets the question bank.
	 * @return a map representing the question bank.
	 */
	public static Map<String, AttributeQuestion> getQuestions() {
		return questionBank;
	}

}
