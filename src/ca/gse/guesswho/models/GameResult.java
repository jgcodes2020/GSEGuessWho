/*
GameResult.java
Author: Jacky Guo
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models;

/**
 * Game result.
 */
public class GameResult {
    private String name;
    private boolean isAISmart;
    private int turnCount;
    private long winTime;

    /**
     * Creates a new leaderboard entry.
     * 
     * @param name      the name of the winner
     * @param isAISmart whether they played against smart AI
     * @param turnCount the number of turns taken to win
     * @param winTime   the time taken to win, as a number of milliseconds.
     */
    public GameResult(String name, boolean isAISmart, int turnCount, long winTime) {
        this.name = name;
        this.isAISmart = isAISmart;
        this.turnCount = turnCount;
        this.winTime = winTime;
    }

    /**
     * Parses a leaderboard entry from a row of CSV.
     * 
     * @param row the row
     * @return the leaderboard entry
     */
    public static GameResult fromCsvRow(String row) {
        String[] parts = row.split(",");
        if (parts.length != 4)
            throw new IllegalArgumentException("Invalid CSV row: not exactly 4 fields");

        // create the object based on the provided parts
        return new GameResult(
				// name
                parts[0],
				// is AI smart
                Boolean.parseBoolean(parts[1]),
				// turns taken to win
                Integer.parseInt(parts[2]),
				// time to win
                Utilities.stringToMillis(parts[3]));
    }

    /**
     * Converts a leaderboard entry to a row of CSV.
     * 
     * @return a row of CSV for the leaderboard entry.
     */
    public String toCsvRow() {
        // example: Chapman,true,8,00:03:02.105
        return String.format("%s,%s,%s,%s", name, isAISmart, turnCount, Utilities.millisToString(winTime));
    }

    /**
     * Gets the name for this leaderboard entry
     * 
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets whether the game was played against smart AI.
     * 
     * @return true if the AI is smart, false if not
     */
    public boolean getIsAISmart() {
        return isAISmart;
    }

    /**
     * Gets the number of turns taken.
     * 
     * @return the number of turns taken.
     */
    public int getTurnCount() {
        return turnCount;
    }

	/**
	 * Gets the amount of time taken to win this game.
	 * @return the amount of time, in milliseconds.
	 */
    public long getWinTime() {
        return winTime;
    }
}
