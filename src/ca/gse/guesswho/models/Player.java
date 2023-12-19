package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.List;

public abstract class Player {
	protected BitSet remainingIndexes;
	protected int secretIndex;
	
	public abstract Question takeTurn();
	public abstract boolean isHuman();
	public abstract String getName();
	
	protected Player() {
		List<GuessWhoCharacter> characters = GameState.getCharacterList();
		
		if (characters == null)
			throw new IllegalArgumentException("Player creation requires a loaded character list!");
		
		// create and fill the "remaining indexes" array with true
		this.remainingIndexes = new BitSet(characters.size());
		this.remainingIndexes.set(0, characters.size());
		// set the secret index to -1 as a sentinel value
		this.secretIndex = -1;
	}
	
	public void setSecretIndex(int secretIndex) {
		this.secretIndex = secretIndex;
	}
	
	public int getSecretIndex() {
		return this.secretIndex;
	}
	
	public BitSet getRemainingIndexes() {
		return this.remainingIndexes;
	}
}
