package ca.gse.guesswho.models.questions;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

/**
 * A question that guesses a specific character. Ends the game immediately.
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
	
	/**
	 * Returns the character being guessed.
	 * @return the character being guessed.
	 */
	public GuessWhoCharacter getCharacter() {
		return character;
	}
	
	/**
	 * Checks whether this object is equal to another object. Two
	 * character questions are equal if they refer to the same character.
	 * @return true if {@code thatObj} is equal to this object.
	 */
	@Override
	public boolean equals(Object thatObj) {
		if (thatObj == null || !(thatObj instanceof CharacterQuestion))
			return false;
		
		CharacterQuestion that = (CharacterQuestion) thatObj;
		return this.character == that.character;
	}
	
	@Override
	public int hashCode() {
		// invert and offset the hash code so that the question hashes differently
		// than the character.
		return ~character.hashCode() + 0xA455;
	}
}
