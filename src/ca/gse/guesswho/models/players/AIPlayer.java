package ca.gse.guesswho.models.players;

import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;

public abstract class AIPlayer extends Player {
	
	private String name;
	private GuessWhoCharacter secret;
	
	protected AIPlayer(String name, GuessWhoCharacter secret) {
		this.name = name;
	}

	@Override
	public abstract Question askQuestion();

	@Override
	public boolean answerQuestion(Question question) {
		return question.match(secret);
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getName'");
	}
	
}
