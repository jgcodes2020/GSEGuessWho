package ca.gse.guesswho.models;

/**
 * Base class for all Guess Who questions.
 */
public abstract class Question {
	/**
	 * Checks if a character matches on this question (i.e. the character
	 * would give "yes" when asked this question).
	 * @param character the character to match
	 * @return true if the character matches, false otherwise.
	 */
	public abstract boolean match(GuessWhoCharacter character);
	/**
	 * Checks whether this question is final (i.e. an answer of "no"
	 * will cause the asking player to lose).
	 * @return true if the question is final, false otherwise.
	 */
	public abstract boolean getIsFinal();
}
