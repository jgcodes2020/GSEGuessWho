package ca.gse.guesswho.models.history;

import ca.gse.guesswho.models.Question;

/**
 * An entry in the game history.
 */
public class GameHistoryEntry {
	private Question question;
	private boolean answer;
	
	public GameHistoryEntry(Question question, boolean answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public Question getQuestion() {
		return question;
	}
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
