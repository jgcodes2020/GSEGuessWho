package ca.gse.guesswho.models.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.questions.AttributeQuestion;
import ca.gse.guesswho.models.questions.FinalQuestion;

public class DumbAIPlayer extends Player {
	private Random rng;

	/**
	 * Creates a new dumb AI player.
	 */
	public DumbAIPlayer() {
		rng = new Random();
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
	public Question takeTurn() {
		List<GuessWhoCharacter> characters = GameState.getCharacterList();

		// if we're down to one option, use that one
		if (remainingIndexes.cardinality() == 1) {
			int finalIndex = remainingIndexes.nextSetBit(0);
			return new FinalQuestion(characters.get(finalIndex));
		}

		// pick a random attribute and valid value for that attribute.
		// This does not take into account what is known about the set of remaining characters,
		// and as such, is very, very dumb.
		int randomAttr = rng.nextInt(GuessWhoCharacter.ATTRIBUTE_NUM_VALS);
		byte randomValue = (byte) rng.nextInt(GuessWhoCharacter.attributeMaxValue(randomAttr));
		return new AttributeQuestion(randomAttr, randomValue);
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
	public String getName() {
		// TODO: should we add actual names or leave this one?
		return "NoobGamer69420";
	}

}
