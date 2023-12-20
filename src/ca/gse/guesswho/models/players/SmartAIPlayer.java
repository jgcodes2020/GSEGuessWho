package ca.gse.guesswho.models.players;

import java.util.BitSet;
import java.util.List;
import java.util.Random;

import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;

public class SmartAIPlayer extends Player {
	private int turnCount;
	private int[][] frequencyList;

	/**
	 * Creates a new smart AI player.
	 */
	public SmartAIPlayer() {
		turnCount = 0;
		
		List<GuessWhoCharacter> characterList = GameState.getCharacterList();
		
		frequencyList = new int[GuessWhoCharacter.ATTRIBUTE_NUM_VALS][];
		for (int i = 0; i < frequencyList.length; i++) {
			frequencyList[i] = new int[GuessWhoCharacter.attributeMaxValue(i)];
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
		
		// reset the frequency list values to 0
		for (int i = 0; i < frequencyList.length; i++) {
			for (int j = 0; j < frequencyList.length; j++) {
				frequencyList[i][j] = 0;
			}
		}
		
		// 
		for (int i = remainingIndexes.nextSetBit(0); i != Integer.MAX_VALUE; i = remainingIndexes.nextSetBit(i + 1)) {
			
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
