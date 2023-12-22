package ca.gse.guesswho.events;

import java.util.EventObject;

/**
 * Event object that represents a game being won.
 * This is modeled after Java's own event object system.
 */
public class GameWonEvent extends EventObject {
	boolean winnerP1;
	
	/**
	 * Constructs a GameWinnerEvent that represents a game being won.
	 * @param source the source of the event
	 * @param winnerP1 If true, the winner is player 1.
	 */
	public GameWonEvent(Object source, boolean winnerP1) {
		super(source);
		this.winnerP1 = winnerP1;
	}
	
	/**
	 * Checks if the winner was player 1 or not.
	 * @return true if player 1 was the winner, false if player 2 was the winner
	 */
	public boolean isWinnerP1() {
		return winnerP1;
	}
}
