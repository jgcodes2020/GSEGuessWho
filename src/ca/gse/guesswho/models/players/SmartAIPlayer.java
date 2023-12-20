package ca.gse.guesswho.models.players;

import java.util.BitSet;
import java.util.List;
import java.util.Random;

import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;

public class SmartAIPlayer extends Player {
	private int turnCount;
	private BitSet[] remainingAttributes;

	/**
	 * Creates a new smart AI player.
	 */
	public SmartAIPlayer() {
		turnCount = 0;
		
		List<GuessWhoCharacter> characterList = GameState.getCharacterList();
		
		remainingAttributes = new BitSet[GuessWhoCharacter.ATTRIBUTE_NUM_VALS];
		for (int i = 0; i < remainingAttributes.length; i++) {
			remainingAttributes[i] = new BitSet();
		}
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
			return new CharacterQuestion(characters.get(finalIndex));
		}
		
		// The first question can always be about gender
		if (turnCount == 0) {
			turnCount++;
			return new AttributeQuestion(GuessWhoCharacter.ATTRIBUTE_GENDER, GuessWhoCharacter.GENDER_MALE);
		}
		
		
		
		throw new UnsupportedOperationException("TO BE IMPLEMENTED!!");
	}

	/**
	 * Always returns false, since this is an AI playe
	 * @return false
	 */
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
