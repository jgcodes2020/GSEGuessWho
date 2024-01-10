package ca.gse.guesswho.models.players;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;

/**
 * Base class for AI players. AI players know their secret character and answer
 * questions by matching against the database.
 */
public abstract class AIPlayer extends Player {
	
	private String name;
	private GuessWhoCharacter secret;
	
	/**
	 * Creates a new AIPlayer
	 * @param name Their name
	 * @param secret Their assigned character
	 */
	protected AIPlayer(String name, GuessWhoCharacter secret) {
		this.name = name;
		this.secret = secret;
	}

	/**
	 * Gets the player's next question.
	 * @return the next question
	 */
	@Override
	public abstract Question askQuestion();
	/**
	 * Answers the last question asked; the last question is
	 * typically made available to the game state
	 * @param question the last question asked.
	 * @return the answer to the question.
	 */
	@Override
	public boolean answerQuestion(Question question) {
		return question.match(secret);
	}
	/**
	 * Checks if this player is human. Human players generally
	 * ask and/or answer through user input.
	 * @return false (The AIPlayer is not a human)
	 */
	@Override
	public boolean isHuman() {
		return false;
	}
	/**
	 * Gets this player's name. This is not related to the character
	 * they choose; and merely acts as something they know.
	 * @return the player's name.
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
