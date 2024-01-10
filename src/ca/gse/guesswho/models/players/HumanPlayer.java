package ca.gse.guesswho.models.players;

import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;

/**
 * Represents a Human player.
 */
public class HumanPlayer extends Player {
	private String name;
	private Question nextQuestion;
	
	private boolean nextAnswer;
	private boolean hasNextAnswer;
	
	/**
	 * Creates a new human player with the given name.
	 * @param name the name
	 */
	public HumanPlayer(String name) {
		this.name = name;
		this.nextQuestion = null;
		
		this.nextAnswer = false;
		this.hasNextAnswer = false;
	}

	/**
	 * Queues the next question. Only one question may be queued,
	 * setting the next question while one is queued overrides it.
	 * @param question the question to queue.
	 */
	public void setNextQuestion(Question question) {
		nextQuestion = question;
	}
	
	/**
	 * Asks the next queued question if there is one, or throws if not.
	 * @return the next question
	 * @throws IllegalStateException if no next question has been queued.
	 */
	@Override
	public Question askQuestion() {
		if (nextQuestion == null)
			throw new IllegalStateException("No next question is queued");
		
		Question result = nextQuestion;
		nextQuestion = null;
		return result;
	}
	
	/**
	 * Queues the next answer. Only one answer may be queued,
	 * setting the next answer while one is queued overrides it.
	 * @param answer the answer to queue.
	 */
	public void setNextAnswer(boolean answer) {
		this.nextAnswer = answer;
		this.hasNextAnswer = true;
	}

	/**
	 * Returns the next queued answer if there is one, or throws if not.
	 * @return the next answer
	 * @throws IllegalStateException if no next answer has been queued.
	 */
	@Override
	public boolean answerQuestion(Question question) {
		if (!hasNextAnswer)
			throw new IllegalStateException("No next answer is queued");
		hasNextAnswer = false;
		return nextAnswer;
	}
	
	/**
	 * Human players are human, so this returns true.
	 * @return true
	 */
	@Override
	public boolean isHuman() {
		return true;
	}

	/**
	 * Gets this player's name.
	 * @return this player's name
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
}
