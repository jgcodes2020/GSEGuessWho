package ca.gse.guesswho.models.players;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import ca.gse.guesswho.models.Utilities;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.QuestionBankEntry;
import ca.gse.guesswho.models.questions.AttributeQuestion;
import ca.gse.guesswho.models.questions.CharacterQuestion;

public class DumbAIPlayer extends AIPlayer {
	private Random rng;
	private int[] order;
	private int index;

	/**
	 * Creates a new dumb AI player.
	 */
	public DumbAIPlayer(String name, GuessWhoCharacter secret) {
		super(name, secret);
		rng = new Random();
		List<QuestionBankEntry> questionList = DataCaches.getQuestionBank();
		
		// predetermine a random order to ask the questions in.
		// This guarantees a) no question is asked twice, b) all questions are in the bank.
		order = new int[questionList.size()];
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		Utilities.shuffle(order);
		
		// current position in the order list.
		index = 0;
	}

	/**
	 * {@inheritDoc}
	 * Dumb AI players are incredibly basic.
	 * <ul>
	 * <li>if there is only one option for them to pick, they make their guess.</li>
	 * <li>otherwise, ask a random question that may or may not narrow down the set
	 * of possible characters.</li>
	 * </ul>
	 */
	@Override
	public Question askQuestion() {
		List<GuessWhoCharacter> characters = DataCaches.getCharacterList();

		int numRemaining = remainingIndexes.cardinality();
		// if we have no options, then logical contradiction!
		if (numRemaining == 0) {
			throw new IllegalStateException("Logical contradiction!");
		}
		// if we're down to one option, guess that one
		if (numRemaining == 1) {
			int finalIndex = remainingIndexes.nextSetBit(0);
			return new CharacterQuestion(characters.get(finalIndex));
		}
		
		int nextIndex = order[index++];
		Question next = DataCaches.getQuestionBank().get(nextIndex).getQuestionObject();
		return next;
	}

	/**
	 * Always returns false, since this is an AI playe
	 * @return false
	 */
	@Override
	public boolean isHuman() {
		return false;
	}

}
