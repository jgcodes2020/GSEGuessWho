package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.List;
import java.util.Random;

import ca.gse.guesswho.models.players.HumanPlayer;

public class GameState {
	private static Random rng = new Random();

	private Player player1;
	private Player player2;

	private byte winner;
	private boolean isPlayer1Turn;
	
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

		// draw a random card for the players who don't have one yet
		if (this.player1.getSecretIndex() < 0) {
			this.player1.setSecretIndex(rng.nextInt(characterList.size()));
		}
		if (this.player2.getSecretIndex() < 0) {
			this.player2.setSecretIndex(rng.nextInt(characterList.size()));
		}
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
		return isPlayer1Turn ? player1 : player2;
	}

	public boolean getPlayer1Turn() {
		return isPlayer1Turn;
	}
	/**
	 * Performs the next player's turn.
	 */
	public void doNextTurn() {
		List<GuessWhoCharacter> characterList = DataCaches.getCharacterList();
		
		// determine who is asking a question and who is answering the question
		Player asking, answering;
		if (this.isPlayer1Turn) {
			asking = player1;
			answering = player2;
		} else {
			asking = player2;
			answering = player1;
		}

		lastQuestion = asking.takeTurn();
		GuessWhoCharacter secretCharacter = characterList.get(answering.getSecretIndex());
		boolean matchValue = lastQuestion.match(secretCharacter);
		
		lastAnswer = matchValue;
		if (lastQuestion.getIsFinal()) {
			// player 1 wins if it is their turn and the character matches, or if it is the
			// other person's turn
			// and the character doesn't match. This is equivalent to A XNOR B or NOT (A XOR
			// B).
			boolean isP1Winner = !(this.isPlayer1Turn ^ matchValue);
			winner = isP1Winner ? WINNER_P1 : WINNER_P2;
		} else {
			BitSet remainingIndexes = asking.getRemainingIndexes();
			for (int i = 0; i < remainingIndexes.length(); i++) {
				// if the current character doesn't match the same as the secret character, flip
				// it down
				if (lastQuestion.match(characterList.get(i)) != matchValue)
					remainingIndexes.clear(i);
			}
		}
		// switch turns
		this.isPlayer1Turn = !this.isPlayer1Turn;
	}

	public boolean getLastAnswer() {//So turningn the awnser of the question to yes or no.
		return lastAnswer;
	}

	public Question getLastQuestion() {
		return lastQuestion;
	}
}
