package ca.gse.guesswho.models.history;

import ca.gse.guesswho.models.Question;

/**
 * An entry in the game history.
 */
public class GameHistoryEntry {
	private Question question;
	private boolean answer;
	

	/**
	 * Creates a instance of history entry.	 * 
	 * @param question The question which was asked
	 * @param answer The answer to the question.
	 */
	public GameHistoryEntry(Question question, boolean answer) {
		this.question = question;
		this.answer = answer;
	}
	/**
	 * Gets the question of the entry
	 * @return the question of the entry
	 */
	public Question getQuestion() {
		return question;
	}


	/**
	 * Gets the answer of the entry
	 * @return the answer of the entry
	 */
	public boolean getAnswer() {
		return answer;
	}
	
	@Override
	public String toString() {
		// example: Is your character male? -> Yes
		
		String answerText;
		if (answer) {
			answerText = "Yes";
		}
		else {
			answerText = "No";
		}
		return String.format("%s -> %s", question, answerText);
	}
}
