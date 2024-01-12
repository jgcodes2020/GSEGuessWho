/*
GameState.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.List;

/**
 * Holds the internal state of a Guess Who game. Handles all turn logic, serving as
 * a frontend-agnostic model of a game of Guess Who.
 */
public class GameState {
	private Player player1;
	private Player player2;


	private byte winner;
	private boolean isPlayer1Turn;
	private boolean isAnswerPhase;
	
	private boolean lastAnswer;
	private Question lastQuestion;
	
	private int turnCount;
	
	public static final byte WINNER_NONE = 0;
	public static final byte WINNER_P1 = 1;
	public static final byte WINNER_P2 = 2;

	/**
	 * Creates an instance of a Guess Who game with two players. The first player
	 * goes first.
	 * 
	 * @param player1 the first player
	 * @param player2 the second player
	 * @param isP1First if true, player 1 goes first, otherwise player 2 goes first.
	 */
	public GameState(Player player1, Player player2, boolean isP1First) {
		List<GuessWhoCharacter> characterList = DataCaches.getCharacterList();
		if (characterList == null) {
			throw new IllegalStateException("Character list must be loaded before starting a game!");
		}

		this.player1 = player1;
		this.player2 = player2;

		winner = WINNER_NONE;
		isPlayer1Turn = isP1First;
		isAnswerPhase = false;
		
		turnCount = 1;
	}

	/**
	 * Gets the current winner of the game.
	 * 
	 * @return the game's winner
	 * @see GameState#WINNER_NONE
	 * @see GameState#WINNER_P1
	 * @see GameState#WINNER_P2
	 */
	public byte getWinner() {
		return winner;
	}
	
	/**
	 * Gets Player 1 for this game.
	 * @return
	 */
	public Player getPlayer1() {
		return player1;
	}
	/**
	 * Gets Player 2 for this game.
	 * @return Player 2
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * Gets the current player.
	 * @return the current player.
	 */
	public Player getCurrentPlayer() {
		if (isPlayer1Turn)
			return player1;
		else
			return player2;
	}

	/**
	 * Checks whether it is player 1's turn or not.
	 * @return true if it is player 1's turn, false if it is player 2's turn.
	 */
	public boolean getPlayer1Turn() {
		return isPlayer1Turn;
	}
	
	/**
	 * Checks whether the next pending phase is an answer or a question.
	 * @return true if the next phase is an answer, false if it is a question.
	 */
	public boolean getIsAnswerPhase() {
		return isAnswerPhase;
	}
	/**
	 * Executes the next phase of a turn, i.e. a single question or a single answer.
	 */
	public void doNextPhase() {
		List<GuessWhoCharacter> characterList = DataCaches.getCharacterList();
		
		Player currentPlayer, otherPlayer;
		if (isPlayer1Turn){
			currentPlayer = player1;
			otherPlayer = player2;
		}
		else{
			currentPlayer = player2;
			otherPlayer = player1;
		}
			
		if (isAnswerPhase) {
			// answer phase
			lastAnswer = currentPlayer.answerQuestion(lastQuestion);
			// final questions either win or lose
			if (lastQuestion.getIsFinal()) {
				// cool logic thing. see note 1 in README.
				boolean isP1Winner = isPlayer1Turn ^ lastAnswer;
				if (isP1Winner) {
					winner = WINNER_P1;
				}
				else {
					winner = WINNER_P2;
				}
				// don't do anything, the winner is determined
				return;
			}
			// clear the other player's remaining indexes
			BitSet remaining = otherPlayer.getRemainingIndexes();
			int remainingLength = remaining.length();
			for (int i = 0; i < remainingLength; i++) {
				GuessWhoCharacter c = characterList.get(i);
				if (lastQuestion.match(c) != lastAnswer) {
					remaining.clear(i);
				}
			}
			// same player
			isAnswerPhase = false;
			// the "turn" ends after an answer
			turnCount++;
		}
		else {
			// just ask the question
			lastQuestion = currentPlayer.askQuestion();
			// other player answers after this
			isPlayer1Turn = !isPlayer1Turn;
			isAnswerPhase = true;
		}
	}

	/**
	 * Returns the last answer answered.
	 * @return the last answer: true if yes, false if no.
	 */
	public boolean getLastAnswer() {
		return lastAnswer;
	}

	/**
	 * Returns the last question asked.
	 * @return the last question.
	 */
	public Question getLastQuestion() {
		return lastQuestion;
	}
	
	/**
	 * Gets the number of completed turns. A turn consists of one question and one answer.
	 * @return the number of completed turns
	 */
	public int getTurnCount() {
		return turnCount;
	}
}
