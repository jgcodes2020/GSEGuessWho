package ca.gse.guesswho.models;

public abstract class Player {
	protected boolean[] remainingIndexes;
	protected int secretIndex;
	
	public abstract Question takeTurn();
	public abstract boolean isHuman();
	public abstract String getName();
	
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
