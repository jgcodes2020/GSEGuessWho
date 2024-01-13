/*
GameHistory.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models.history;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.Utilities;

/**
 * Object recording all questions and answers, plus various statistics.
 */
public class GameHistory {
	private String p1Name;
	private GuessWhoCharacter p1Secret;
	private boolean p1IsAI;

	private String p2Name;
	private GuessWhoCharacter p2Secret;
	private boolean p2IsAI;

	private boolean isP1First;
	private boolean isWinnerP1;

	private boolean isForfeit;
	private boolean isLogicalError;

	private long winTime;

	private Question nextQuestion;
	private List<GameHistoryEntry> entryList;

	/**
	 * Constructs a new GameHistory.
	 * 
	 * @param p1Name    the name assigned to player 1.
	 * @param p2Name    the name assigned to player 2.
	 * @param isP1First whether Player 1 goes first.
	 */
	public GameHistory(String p1Name, String p2Name, boolean isP1First) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.isP1First = isP1First;

		nextQuestion = null;
		entryList = new ArrayList<>();

		p1Secret = null;
		p1IsAI = false;

		p2Secret = null;
		p2IsAI = false;

		isForfeit = false;
		isLogicalError = false;

		winTime = -1L;
	}

	/**
	 * Adds a question to the history.
	 * 
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
	 * 
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
	 * 
	 * @return the entries in this history.
	 */
	public List<GameHistoryEntry> getEntries() {
		return Collections.unmodifiableList(entryList);
	}

	/**
	 * Saves this game history to a text file.
	 * 
	 * @param file the file to save to.
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
			String p1Type, p2Type;
			if (p1IsAI)
				p1Type = "AI";
			else
				p1Type = "Human";
			if (p2IsAI)
				p2Type = "AI";
			else
				p2Type = "Human";
			pw.printf(
					"P1 (%s, %s) chose %s\n" +
							"P2 (%s, %s) chose %s\n\n",
					p1Name, p1Type, p1CharacterName,
					p2Name, p2Type, p2CharacterName);
			// print stats
			pw.printf(
					"Total turns taken: %s\n" +
							"Time to win: %s\n\n",
					entryList.size(), Utilities.millisToString(winTime));
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

			// print winner (and forfeiting if needed)
			pw.println();
			if (isWinnerP1) {
				if (isForfeit) {
					pw.println("P2 Forfeited");
				}
				pw.println("Winner: P1");
			} else {
				if (isForfeit) {
					pw.println("P1 Forfeited");
				}
				pw.println("Winner: P2");
			}

			if (isLogicalError){
				pw.println("Warning: Logical Error has happened.");
			}
		}

	}

	/**
	 * Gets Player 1's secret character.
	 * 
	 * @return Player 1's secret character, or null if one wasn't set
	 */
	public GuessWhoCharacter getP1Secret() {
		return p1Secret;
	}

	/**
	 * Sets Player 1's secret character.
	 * 
	 * @param p2Secret Player 1's secret character.
	 */
	public void setP1Secret(GuessWhoCharacter p1Secret) {
		this.p1Secret = p1Secret;
	}

	/**
	 * Gets Player 2's secret character.
	 * 
	 * @return Player 2's secret character, or null if one wasn't set
	 */
	public GuessWhoCharacter getP2Secret() {
		return p2Secret;
	}

	/**
	 * Sets Player 2's secret character.
	 * 
	 * @param p2Secret Player 2's secret character.
	 */
	public void setP2Secret(GuessWhoCharacter p2Secret) {
		this.p2Secret = p2Secret;
	}

	/**
	 * Checks if Player 1 won this game.
	 * 
	 * @return if Player 1 won this game.
	 */
	public boolean getIsWinnerP1() {
		return isWinnerP1;
	}

	/**
	 * Sets whether Player 1 won this game.
	 * 
	 * @param isP1Winner true if Player 1 is the winner, false if Player 2 is the
	 *                   winner.
	 */
	public void setIsWinnerP1(boolean isP1Winner) {
		this.isWinnerP1 = isP1Winner;
	}

	/**
	 * Gets player 1's name.
	 * 
	 * @return player 1's name.
	 */
	public String getP1Name() {
		return p1Name;
	}

	/**
	 * Gets player 2's name
	 * 
	 * @return player 2's name
	 */
	public String getP2Name() {
		return p2Name;
	}

	/**
	 * Gets the number of turns taken during the game.
	 * 
	 * @return the number of turns taken during the game.
	 */
	public int getTurnCount() {
		return entryList.size();
	}

	/**
	 * Gets the time taken to win the game.
	 * 
	 * @return the time taken to win in milliseconds, or -1 if not set
	 */
	public long getWinTime() {
		return winTime;
	}

	/**
	 * Sets the time taken to win the game.
	 * 
	 * @param winTime the time taken to win, in milliseconds
	 */
	public void setWinTime(long winTime) {
		this.winTime = winTime;
	}

	/**
	 * Gets whether player 1 was an AI.
	 * 
	 * @return if true, player 1 was an AI.
	 */
	public boolean getP1IsAI() {
		return p1IsAI;
	}

	/**
	 * Sets whether player 1 was an AI.
	 * 
	 * @param p1IsAI if true, player 1 was an AI.
	 */
	public void setP1IsAI(boolean p1IsAI) {
		this.p1IsAI = p1IsAI;
	}

	/**
	 * Gets whether player 2 was an AI.
	 * 
	 * @return if true, player 2 was an AI.
	 */
	public boolean getP2IsAI() {
		return p2IsAI;
	}

	/**
	 * Sets whether player 2 was an AI.
	 * 
	 * @param p1IsAI if true, player 2 was an AI.
	 */
	public void setP2IsAI(boolean p2IsAI) {
		this.p2IsAI = p2IsAI;
	}

	/**
	 * Gets whether the game was ended by forfeit.
	 * @return if true, the game was ended by forfeit.
	 */
	public boolean getForfeit() {
		return isForfeit;
	}

	/**
	 * Sets whether the game was ended by forfeit.
	 * @param forfeit if true, the game was ended by forfeit.
	 */
	public void setForfeit(boolean forfeit) {
		this.isForfeit = forfeit;
	}

	public void setLogical(boolean errors){
		this.isLogicalError = errors;

	}

}
