package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.List;

public abstract class Player {
	protected BitSet remainingIndexes;
	
	public abstract Question askQuestion();
	public abstract boolean answerQuestion(Question question);
	
	public abstract boolean isHuman();
	public abstract String getName();
	
	
	protected Player() {
		List<GuessWhoCharacter> characters = DataCaches.getCharacterList();
		
		if (characters == null)
			throw new IllegalArgumentException("Player creation requires a loaded character list!");
		
		// create and fill the "remaining indexes" array with true
		this.remainingIndexes = new BitSet(characters.size());
		this.remainingIndexes.set(0, characters.size());
	}
	
	/**
	 * Returns a bitset containing the remaining indexes for this player
	 * @return the remaining indexes for this player
	 * @see DataCaches#getCharacterList()
	 */
	public BitSet getRemainingIndexes() {
		return this.remainingIndexes;
	}
}
