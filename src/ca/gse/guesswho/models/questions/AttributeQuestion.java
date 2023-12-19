package ca.gse.guesswho.models.questions;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

/**
 * Represents a question about a specific attribute.
 */
public class AttributeQuestion extends Question {
	private int attribute;
	private byte checkedValue;

	/**
	 * Creates a new attribute question for a specific attribute and value.
	 * @param attribute the attribute (see {@code GuessWhoCharacter.ATTRIBUTE_*} constants)
	 * @param checkedValue the value to check for. Must be valid for the given attribute.
	 * @throws IllegalArgumentException if the attribute or value are invalid.
	 */
	public AttributeQuestion(int attribute, byte checkedValue) {
		if (attribute >= GuessWhoCharacter.ATTRIBUTE_NUM_VALS || attribute < 0)
			throw new IllegalArgumentException("Invalid attribute code " + attribute);
		if (checkedValue >= GuessWhoCharacter.attributeMaxValue(attribute) || checkedValue < 0)
			throw new IllegalArgumentException("Invalid value " + checkedValue + " for attribute " + attribute);
		
		this.attribute = attribute;
		this.checkedValue = checkedValue;
	}

	/**
	 * Mat
	 */
	@Override
	public boolean match(GuessWhoCharacter character) {
		return character.getAttribute(attribute) == checkedValue;
	}

	@Override
	public boolean getIsFinal() {
		return false;
	}
	
}
