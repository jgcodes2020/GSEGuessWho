package ca.gse.guesswho.models.players;

import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;

public class HumanPlayer extends Player {
	private String name;
	private Question nextQuestion;
	
	public HumanPlayer(String name) {
		this.name = name;
		this.nextQuestion = null;
	}
	
	public Question getNextQuestion() {
		return this.nextQuestion;
	}

	public void setNextQuestion(Question question) {
		nextQuestion = question;
	}
	
	@Override
	public Question takeTurn() {
		if (nextQuestion == null)
			throw new IllegalStateException("No next question is queued");
		
		Question result = nextQuestion;
		nextQuestion = null;
		return result;
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
}
