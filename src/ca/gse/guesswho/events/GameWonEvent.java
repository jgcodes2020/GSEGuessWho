/*
GameWonEvent.java
Authors: Jacky Guo
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.events;

import java.util.EventObject;

import ca.gse.guesswho.models.history.GameHistory;

/**
 * Event object for winning a game. Originally, this was part of an event
 * system,
 * though the object remains as it's useful in its own right.
 */
public class GameWonEvent extends EventObject {
    GameHistory history;
    String winnerName;

    /**
     * Constructs a GameWonEvent that represents a game being won.
     * 
     * @param source   the source of the event
     * @param winnerP1 If true, the winner is player 1.
     * @param history  the game history
     */
    public GameWonEvent(Object source, GameHistory history) {
        super(source);
        this.history = history;
		// determine the winner's name
        if (history.getIsWinnerP1()) {
            winnerName = history.getP1Name();
        } else {
            winnerName = history.getP2Name();
        }
    }

    /**
     * Checks if the winner was player 1 or not.
     * 
     * @return true if player 1 was the winner, false if player 2 was the winner
     */
    public boolean isWinnerP1() {
        return history.getIsWinnerP1();
    }

	/**
	 * Returns the history associated with this game.
	 * @return the game history
	 */
    public GameHistory getHistory() {
        return history;
    }

	/**
	 * Returns the name of this game's winner
	 * @return
	 */
    public String getWinnerName() {
        return winnerName;
    }
}
