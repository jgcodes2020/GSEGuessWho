package ca.gse.guesswho.models;

import java.util.Objects;

import ca.gse.guesswho.models.questions.AttributeQuestion;

public class QuestionBankEntry {
	String text;
	AttributeQuestion questionObject;

	/**
	 * Constructs a new pair with the specified elements.
	 * 
	 * @param first  the first element.
	 * @param second the second element.
	 */
	public QuestionBankEntry(String first, AttributeQuestion second) {
		this.text = first;
		this.questionObject = second;
	}

	/**
	 * Constructs a new pair with both values set to null.
	 */
	public QuestionBankEntry() {
		this(null, null);
	}

	/**
	 * Gets the first value.
	 * 
	 * @return the first value.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the second value.
	 * 
	 * @return the second value.
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
	 * Returns a string representation of this pair. It follows the
	 * format: {@code {a, b}}.
	 * @return a string representation of this pair.
	 */
	@Override
	public String toString() {
		return String.format("{%s, %s}", text, questionObject);
	}
}
