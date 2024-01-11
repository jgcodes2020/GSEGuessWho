package ca.gse.guesswho.events;

import java.util.EventObject;

import ca.gse.guesswho.models.history.GameHistory;

/**
 * Event object for winning a game. Originally, this was part of an event system,
 * though the object remains as it's useful in its own right.
 */
public class GameWonEvent extends EventObject {
	GameHistory history;
	String winner;
	
	/**
	 * Constructs a GameWinnerEvent that represents a game being won.
	 * @param source the source of the event
	 * @param winnerP1 If true, the winner is player 1.
	 * @param history the game history
	 */
	public GameWonEvent(Object source, GameHistory history) {
		super(source);
		this.history = history;
	}
	

	/**
	 * Constructs a GameWinnerEvent that represents a game being won.
	 * @param source the source of the event
	 * @param winnerP1 If true, the winner is player 1.
	 * @param history the game history
	 * @param state the state of the game
	 */
	public GameWonEvent(Object source, boolean winnerP1, GameHistory history) {
		super(source);
		if (winnerP1){
			winner = history.getP1Name();
		}
		else{
			winner = history.getP2Name();
		}
		this.history = history;
	}

	/**
	 * Checks if the winner was player 1 or not.
	 * @return true if player 1 was the winner, false if player 2 was the winner
	 */
	public boolean isWinnerP1() {
		return history.isWinnerP1();
	}
	
	public GameHistory getHistory() {
		return history;
	}

	public String getWinnerName() {
		return winner;
	}
}
