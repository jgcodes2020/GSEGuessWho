package ca.gse.guesswho.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ProgressMonitorInputStream;

import ca.gse.guesswho.models.questions.AttributeQuestion;
import ca.gse.guesswho.models.questions.CharacterQuestion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class with helper methods for loading and caching resource data.
 * Also handles several utilities related to those resources.
 * This class should not be instantiated, it is otherwise useless.
 */
public class DataCaches {
	private static List<GuessWhoCharacter> characterList = null;
	private static List<QuestionBankEntry> questionBank = null;
	
	private static Random rng = new Random();
	
	// private constructor: prevents anyone from making DataCaches
	private DataCaches() {}
	

	/**
	 * Loads and parses the character list from a CSV file. This must be done before
	 * creating any {@link GameState} instances.
	 * 
	 * @param path the path to load from.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void loadCharacters(URL path) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new ProgressMonitorInputStream(null, "Loading game...", path.openStream())))) {
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

		// Linear search for the desired character. This should be fine since
		// we don't have that many characters.
		for (GuessWhoCharacter gwCharacter : characterList) {
			if (gwCharacter.getName().equals(name))
				return gwCharacter;
		}
		throw new IllegalArgumentException("There is no characted named " + name);
	}

	/**
	 * Loads and parses the question bank from a CSV file.
	 * 
	 * @param path the path to load from.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void loadQuestions(URL source) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()))) {
			String line;
			List<QuestionBankEntry> questions = new ArrayList<>();
			// read the header row. We could validate it, but I'm not doing that now.
			line = br.readLine();
			if (line == null)
				throw new IllegalArgumentException("Parsing failed! (missing header row)");
			// read each data row, converting it to a key and attribute question.
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				// column 0 contains the question text
				// columns 1 and 2 contain an attribute and value
				String questionText = parts[0];
				AttributeQuestion question = new AttributeQuestion(Integer.parseInt(parts[1]),
						Byte.parseByte(parts[2]));

				questions.add(new QuestionBankEntry(questionText, question));
			}
			// store the question bank as an immutable list, since we don't
			// need to change it anyways.
			questionBank = Collections.unmodifiableList(questions);
		}
	}

	/**
	 * Gets the question bank. The question bank is a list of pairs,
	 * where each pair contains a string (the question text) and an
	 * attribute question (the corresponding question object).
	 * 
	 * @return a map representing the question bank.
	 */
	public static List<QuestionBankEntry> getQuestionBank() {
		return questionBank;
	}
	
	// HELPER METHODS
	//===============
	
	/**
	 * Gets a random character.
	 * @return the random character.
	 */
	public static GuessWhoCharacter randomCharacter() {
		// pick a random index and grab from the character list
		int randIndex = rng.nextInt(characterList.size());
		return characterList.get(randIndex);
	}

	/**
	 * Determine the corresponding text for a question.
	 * 
	 * @param question the question
	 * @return a string representing the question
	 */
	public static String getQuestionString(Question question) {
		if (question instanceof CharacterQuestion) {
			// Character questions have a predictable format.
			return String.format("Is your character %s?", ((CharacterQuestion) question).getCharacter().getName());
		} else if (question instanceof AttributeQuestion) {
			// Attribute questions are represented in the question bank. Since
			// we don't have a hash map, this necessitates linear search; though
			// this is fine since there aren't that many questions anyways.
			for (QuestionBankEntry entry : questionBank) {
				if (entry.getQuestionObject().equals(question)) {
					return entry.getText();
				}
			}
		}
		return question.toString();
	}
}
