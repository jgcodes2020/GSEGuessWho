package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.List;

public class GameState {
	private Player player1;
	private Player player2;

	private byte winner;
	private boolean isPlayer1Turn;
	private boolean isAnswerPhase;
	
	private boolean lastAnswer;
	private Question lastQuestion;
	
	public static final byte WINNER_NONE = 0;
	public static final byte WINNER_P1 = 1;
	public static final byte WINNER_P2 = 2;

	/**
	 * Gets the global character list.
	 * 
	 * @return the global character list, or null if it hasn't been loaded
	 */
	// public static List<GuessWhoCharacter> getCharacterList() {
	// 	return characterList;
	// }

	/**
	 * Creates an instance of a Guess Who game with two players. The first player
	 * goes first.
	 * 
	 * @param player1 the first player
	 * @param player2 the second player
	 */
	public GameState(Player player1, Player player2) {
		List<GuessWhoCharacter> characterList = DataCaches.getCharacterList();
		if (characterList == null) {
			throw new IllegalStateException("Character list must be loaded before starting a game!");
		}

		this.player1 = player1;
		this.player2 = player2;

		winner = WINNER_NONE;
		isPlayer1Turn = true;
		isAnswerPhase = false;
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

	public Player getCurrentPlayer() {
		if (isPlayer1Turn)
			return player1;
		else
			return player2;
	}

	public boolean getPlayer1Turn() {
		return isPlayer1Turn;
	}
	
	public boolean getIsAnswerPhase() {
		return isAnswerPhase;
	}
	/**
	 * Performs the next player's turn.
	 */
	public void doNextTurn() {
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
			for (int i = 0; i < remaining.length(); i++) {
				GuessWhoCharacter c = characterList.get(i);
				if (lastQuestion.match(c) != lastAnswer) {
					remaining.clear(i);
				}
			}
			
			isAnswerPhase = false;
		}
		else {
			// question phase
			lastQuestion = currentPlayer.askQuestion();
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
}
