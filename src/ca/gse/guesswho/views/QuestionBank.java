package ca.gse.guesswho.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.questions.AttributeQuestion;

public class QuestionBank {
	private static Map<String, AttributeQuestion> questionBank = null;
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
