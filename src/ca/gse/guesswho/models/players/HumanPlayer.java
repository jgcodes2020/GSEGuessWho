package ca.gse.guesswho.models.players;

import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;

public class HumanPlayer extends Player {
	private String name;
	private Question nextQuestion;
	
	private boolean nextAnswer;
	private boolean hasNextAnswer;
	
	public HumanPlayer(String name) {
		this.name = name;
		this.nextQuestion = null;
		
		this.nextAnswer = false;
		this.hasNextAnswer = false;
	}
	
	public Question getNextQuestion() {
		return this.nextQuestion;
	}

	public void setNextQuestion(Question question) {
		nextQuestion = question;
	}
	
	@Override
	public Question askQuestion() {
		if (nextQuestion == null)
			throw new IllegalStateException("No next question is queued");
		
		Question result = nextQuestion;
		nextQuestion = null;
		return result;
	}
	
	public void setNextAnswer(boolean answer) {
		this.nextAnswer = answer;
		this.hasNextAnswer = true;
	}

	@Override
	public boolean answerQuestion(Question question) {
		if (!hasNextAnswer)
			throw new IllegalStateException("No next answer is queued");
		hasNextAnswer = false;
		return nextAnswer;
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
