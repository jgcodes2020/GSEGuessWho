package ca.gse.guesswho.models;

import java.util.List;

public abstract class Player {
	protected boolean[] remainingIndexes;
	protected int secretIndex;
	
	public abstract Question takeTurn();
	public abstract boolean isHuman();
	public abstract String getName();
	
	protected Player() {
		List<GuessWhoCharacter> characters = GameState.getCharacterList();
		
		if (characters == null)
			throw new IllegalArgumentException("Player creation requires a loaded character list!");
		
		// create and fill the "remaining indexes" array with true
		this.remainingIndexes = new boolean[characters.size()];
		for (int i = 0; i < remainingIndexes.length; i++) {
			this.remainingIndexes[i] = true;
		}
		// set the secret index to -1 as a sentinel value
		this.secretIndex = -1;
	}
	
	public void setSecretIndex(int secretIndex) {
		this.secretIndex = secretIndex;
	}
	
	public int getSecretIndex() {
		return this.secretIndex;
	}
	
	public boolean[] getRemainingIndexes() {
		return this.remainingIndexes;
	}
}
