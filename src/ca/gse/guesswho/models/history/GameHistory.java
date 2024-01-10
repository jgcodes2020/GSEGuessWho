package ca.gse.guesswho.models.history;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

/**
 * Object recording all actions in a game.
 */
public class GameHistory {
	private String p1Name;
	private String p2Name;
	private boolean isP1First;
	
	private Question nextQuestion;
	private List<GameHistoryEntry> entryList;
	
	private boolean isP1Winner;

	private GuessWhoCharacter p1Secret;
	private GuessWhoCharacter p2Secret;
	
	/**
	 * Constructs a new GameHistory.
	 * @param p1Name the name assigned to player 1.
	 * @param p2Name the name assigned to player 2.
	 * @param isP1First whether Player 1 goes first.
	 */
	public GameHistory(String p1Name, String p2Name, boolean isP1First) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.isP1First = isP1First;
		
		nextQuestion = null;
		entryList = new ArrayList<>();
		
		p1Secret = null;
		p2Secret = null;
	}
	
	/**
	 * Adds a question to the history.
	 * @param question the question.
	 * @throws IllegalStateException if an answer is expected
	 */
	public void push(Question question) {
		if (nextQuestion != null)
			throw new IllegalStateException("Expected an answer");
		nextQuestion = question;
	}
	
	/**
	 * Adds an answer to the history.
	 * @param answer the answer.
	 * @throws IllegalStateException is a question is expected
	 */
	public void push(boolean answer) {
		GameHistoryEntry nextEntry = new GameHistoryEntry(nextQuestion, answer);
		nextQuestion = null;
		
		entryList.add(nextEntry);
	}
	
	/**
	 * Returns a read-only view of the entries in this history.
	 * @return the entries in this history.
	 */
	public List<GameHistoryEntry> getEntries() {
		return Collections.unmodifiableList(entryList);
	}
	
	/**
	 * Saves this game history to a text file.
	 */
	public void saveHistoryTo(File file) throws IOException {
		try (PrintWriter pw = new PrintWriter(file)) {
			// write names/secret characters
			String p1CharacterName, p2CharacterName;
			if (p1Secret != null)
				p1CharacterName = p1Secret.getName();
			else
				p1CharacterName = "???";
			if (p2Secret != null)
				p2CharacterName = p2Secret.getName();
			else
				p2CharacterName = "???";
			pw.printf(
				"P1 (%s) chose %s\n" +
				"P2 (%s) chose %s\n\n", 
				p1Name, p1CharacterName, p2Name, p2CharacterName);
			
			// write question log
			boolean isP1Turn = isP1First;
			String playerString;
			
			for (GameHistoryEntry entry : entryList) {
				if (isP1Turn)
					playerString = "P1";
				else
					playerString = "P2";
					
				// P1: Is your character male? -> Yes
				pw.printf("%s: %s\n", playerString, entry);
				isP1Turn = !isP1Turn;
			}
			
			pw.println();
			if (isP1Winner)
				pw.println("Winner: P1");
			else
				pw.println("Winner: P2");
		}
		
	}
	
	/**
	 * Sets both secret characters as part of this history.
	 * @param p1Secret Player 1's secret character
	 * @param p2Secret Player 2's secret character
	 */
	public void setSecretCharacters(GuessWhoCharacter p1Secret, GuessWhoCharacter p2Secret) {
		this.p1Secret = p1Secret;
		this.p2Secret = p2Secret;
	}
	
	/**
	 * Checks if Player 1 won this game.
	 * @return if Player 1 won this game.
	 */
	public boolean isP1Winner() {
		return isP1Winner;
	}
	
	/**
	 * Sets whether Player 1 won this game.
	 * @param isP1Winner true if Player 1 is the winner, false if Player 2 is the winner.
	 */
	public void setP1Winner(boolean isP1Winner) {
		this.isP1Winner = isP1Winner;
	}
}
