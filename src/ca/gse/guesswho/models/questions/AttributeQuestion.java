package ca.gse.guesswho.models.questions;

import java.util.Objects;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;

/**
 * A question about a specific attribute of a character. It does not end the
 * game.
 */
public class AttributeQuestion extends Question {
    private int attribute;
    private byte checkedValue;

    /**
     * Creates a new attribute question for a specific attribute and value.
     * 
     * @param attribute    the attribute (see {@code GuessWhoCharacter.ATTRIBUTE_*}
     *                     constants)
     * @param checkedValue the value to check for. Must be valid for the given
     *                     attribute.
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

    @Override
    public boolean match(GuessWhoCharacter character) {
        return character.getAttribute(attribute) == checkedValue;
    }

    /**
     * Attribute questions do not end the game when the answer is "no". This method
     * will
     * thus always return false.
     * 
     * @return false
     */
    @Override
    public boolean getIsFinal() {
        return false;
    }

    /**
     * Determines whether an object is semantically equal to this question; that is,
     * it checks
     * if the other object asks for the same attribute and value as this question.
     * 
     * @return whether the supplied object refers to the same question as this one
     */
    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null || !(thatObj instanceof AttributeQuestion))
            return false;

        AttributeQuestion that = (AttributeQuestion) thatObj;
        return (this.attribute == that.attribute) && (this.checkedValue == that.checkedValue);
    }

    @Override
    public int hashCode() {
        // We use Java's built-in hash code combiner here as I can't be
        // bothered to make my own. This is necessary to make it compatible with
        // HashMap.
        return Objects.hash(attribute, checkedValue);
    }

    @Override
    public String toString() {
        return String.format("AttributeQuestion {attr=%s, value=%s}", this.attribute, this.checkedValue);
    }

}
