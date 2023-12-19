package ca.gse.guesswho.models.questions;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

/**
 * Represents a question that guesses a specific character.
 */
public class CharacterQuestion extends Question {
	private GuessWhoCharacter character;

	/**
	 * Creates a character question for a specific character.
	 * 
	 * @param character the specific character to ask about
	 */
	public CharacterQuestion(GuessWhoCharacter character) {
		this.character = character;
	}

	/**
	 * Matches for a specific character. 
	 * 
	 * @param character the character being matched.
	 * @return true if the character in this question matches the character in question
	 * @implNote This uses reference equality to compare
	 * the characters; since we're always using the one from the list, it should
	 * always work.
	 */
	@Override
	public boolean match(GuessWhoCharacter character) {
		// for `Object p, q`, p == q checks if they refer to the exact same object.
		// since all characters should be the same as the ones in the character list,
		// this is fine.
		return this.character == character;
	}

	/**
	 * Character guesses end the game if they are answered yes. This will therefore
	 * always return true.
	 * 
	 * @return true
	 */
	@Override
	public boolean getIsFinal() {
		return true;
	}
}
