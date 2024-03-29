/*
SmartAIPlayer.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models.players;

import java.util.BitSet;
import java.util.List;
import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;

/**
 * Creates a smart AI that takes the optimal strategy to guess the opponent's
 * character.
 * (yes, it's optimal, see https://doi.org/10.1371/journal.pone.0247361)
 */
public class SmartAIPlayer extends AIPlayer {
	// list of bitmasks representing the characters that match each question.
	// each element corresponds to a question bank entry; each bit of that element
	// corresponds to a character.
	private static BitSet[] questionPatterns = null;

	/**
	 * Constructs a series of bitmasks representing the outcome of each question on
	 * a full board. This is used to efficiently evaluate questions when making decisions.
	 */
	public static void setupQuestionPatterns() {
		List<QuestionBankEntry> questionBank = DataCaches.getQuestionBank();
		List<GuessWhoCharacter> characters = DataCaches.getCharacterList();

		if (questionBank == null)
			throw new IllegalStateException("Question bank is not loaded!");

		questionPatterns = new BitSet[questionBank.size()];
		for (int i = 0; i < questionPatterns.length; i++) {
			AttributeQuestion question = questionBank.get(i).getQuestionObject();
			questionPatterns[i] = new BitSet(characters.size());

			// evaluate the question for each character and record the ones that match
			for (int j = 0; j < characters.size(); j++) {
				questionPatterns[i].set(j, question.match(characters.get(j)));
			}
		}
	}

	/**
	 * Creates a new smart AI player.
	 */
	public SmartAIPlayer(String name, GuessWhoCharacter secret) {
		super(name, secret);
		if (questionPatterns == null)
			setupQuestionPatterns();
	}

	/**
	 * Asks the smart AI's next question.
	 * 
	 * @return the smart AI's next question
	 * @implSpec The current implementation loops through all available questions,
	 *           selecting the one which gets closest to a 50/50 split. This is the
	 *           optimal
	 *           strategy for a randomly-drawn card; if the selection is biased, it
	 *           becomes
	 *           suboptimal.
	 */
	@Override
	public Question askQuestion() {
		int numRemaining = remainingIndexes.cardinality();
		// if we have no options, then logical contradiction!
		if (numRemaining == 0) {
			throw new IllegalStateException("Logical contradiction!");
		}
		// if we're down to one option, guess that one
		if (numRemaining == 1) {
			int finalIndex = remainingIndexes.nextSetBit(0);
			return new CharacterQuestion(DataCaches.getCharacterList().get(finalIndex));
		}

		// the optimal split will cut our character count in half each time.
		int halfRemaining = numRemaining / 2;

		// find the best question to ask by linear search.
		int bestIndex = 0;
		int bestSplitDiff;
		// start with index 0.
		{
			// compute the set of remaining characters if the answer is yes.
			BitSet tempBits = (BitSet) questionPatterns[0].clone();
			tempBits.and(remainingIndexes);
			// compute how close to perfectly even the split is.
			bestSplitDiff = Math.abs(halfRemaining - tempBits.cardinality());
		}
		// check all the other indices.
		for (int i = 1; i < questionPatterns.length; i++) {
			// compute the set of remaining characters if the answer is yes.
			BitSet tempBits = (BitSet) questionPatterns[i].clone();
			tempBits.and(remainingIndexes);
			// compute how close to perfectly even the split is.
			int splitDiff = Math.abs(halfRemaining - tempBits.cardinality());
			// check if this is the best split so far.
			if (splitDiff < bestSplitDiff) {
				bestIndex = i;
				bestSplitDiff = splitDiff;
			}
		}

		// pull the corresponding question from the question bank and ask it.
		return DataCaches.getQuestionBank().get(bestIndex).getQuestionObject();
	}

}
