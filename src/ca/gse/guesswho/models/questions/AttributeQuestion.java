package ca.gse.guesswho.models.questions;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

public class AttributeQuestion extends Question {
	private int attribute;
	private byte checkedValue;

	public AttributeQuestion(int attribute, byte checkedValue) {
		this.attribute = attribute;
		this.checkedValue = checkedValue;
	}

	@Override
	public boolean match(GuessWhoCharacter character) {
		return character.getAttribute(attribute) == checkedValue;
	}

	@Override
	public boolean getIsFinal() {
		return false;
	}
	
}
