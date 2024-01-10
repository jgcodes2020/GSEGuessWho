package ca.gse.guesswho.events;

import java.util.EventObject;

import ca.gse.guesswho.models.history.GameHistory;

/**
 * Event object for winning a game. Originally, this was part of an event system,
 * though the object remains as it's useful in its own right.
 */
public class GameWonEvent extends EventObject {
	boolean winnerP1;
	GameHistory history;
	
	/**
	 * Constructs a GameWinnerEvent that represents a game being won.
	 * @param source the source of the event
	 * @param winnerP1 If true, the winner is player 1.
	 */
	public GameWonEvent(Object source, boolean winnerP1, GameHistory history) {
		super(source);
		this.winnerP1 = winnerP1;
		this.history = history;
	}
	
	/**
	 * Checks if the winner was player 1 or not.
	 * @return true if player 1 was the winner, false if player 2 was the winner
	 */
	public boolean isWinnerP1() {
		return winnerP1;
	}
	
	public GameHistory getHistory() {
		return history;
	}
}
