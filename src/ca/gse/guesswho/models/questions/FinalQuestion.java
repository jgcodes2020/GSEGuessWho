package ca.gse.guesswho.models.questions;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

public class FinalQuestion extends Question {
	private GuessWhoCharacter character;
	
	public FinalQuestion(GuessWhoCharacter character) {
		this.character = character;
	}

	@Override
	public boolean match(GuessWhoCharacter character) {
		return this.character == character;
	}

	@Override
	public boolean getIsFinal() {
		return true;
	}
}
