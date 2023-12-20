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
	
	/**
	 * Sets this player's secret character as an index into the character.list
	 * @param secretIndex the secret index to set.
	 * @throws IllegalStateException if the player already has a secret index.
	 * @see GameState#getCharacterList()
	 */
	public void setSecretIndex(int secretIndex) {
		if (this.secretIndex >= 0)
			throw new IllegalStateException("Player already has a secret index!");
		this.secretIndex = secretIndex;
	}
	
	/**
	 * Gets this player's secret index, or -1 if the player does not have a secret index.
	 * @return the secret index set on this character.
	 * @see GameState#getCharacterList()
	 */
	public int getSecretIndex() {
		return this.secretIndex;
	}
	
	/**
	 * Returns a bitset containing the remaining indexes for this player
	 * @return the remaining indexes for this player
	 * @see GameState#getCharacterList()
	 */
	public BitSet getRemainingIndexes() {
		return this.remainingIndexes;
	}
}
