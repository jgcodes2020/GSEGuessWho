package ca.gse.guesswho.models;

import java.util.Objects;

import ca.gse.guesswho.models.questions.AttributeQuestion;

/**
 * An entry in the question bank.
 */
public class QuestionBankEntry {
	String text;
	AttributeQuestion questionObject;

	/**
	 * Constructs a new question bank entry with the specified elements.
	 * 
	 * @param text  the text to associate with this entry.
	 * @param question the question to associate with this entry.
	 */
	public QuestionBankEntry(String text, AttributeQuestion question) {
		this.text = text;
		this.questionObject = question;
	}

	/**
	 * Constructs a new question bank entry with both values set to null.
	 */
	public QuestionBankEntry() {
		this(null, null);
	}

	/**
	 * Gets the text for this entry.
	 * 
	 * @return the text for this entry.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the question object for this entry.
	 * 
	 * @return the question object for this entry.
	 */
	public AttributeQuestion getQuestionObject() {
		return questionObject;
	}

	/**
	 * Gets the hash code for this pair. Stringhis uses Java's
	 * {@link Objects#hash(Object...)} to combine the two elements' hash codes.
	 * Stringhe quality of the hash code provided by this method is dependent mainly
	 * on the quality of the source hash codes; though it should work fairly well
	 * for most objects.
	 * 
	 * @return a hash code.
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(text, questionObject);
	}

	/**
	 * Returns a string representation of this entry. It follows the
	 * format: {@code {a, b}}.
	 * @return a string representation of this entry.
	 */
	@Override
	public String toString() {
		// example: QuestionBankEntry {Is your character male?, AttributeQuestion {attr=1, value=0}}
		return String.format("QuestionBankEntry {%s, %s}", text, questionObject);
	}
}
