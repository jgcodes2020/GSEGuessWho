package ca.gse.guesswho.models;

import java.time.Duration;

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
	 * @param name the name of the winner
	 * @param isAISmart whether they played against smart AI
	 * @param turnCount the number of turns taken to win
	 * @param winTime the time taken to win, as a number of milliseconds.
	 */
	public GameResult(String name, boolean isAISmart, int turnCount, long winTime) {
		this.name = name;
		this.isAISmart = isAISmart;
		this.turnCount = turnCount;
		this.winTime = winTime;
	}
	
	/**
	 * Parses a leaderboard entry from a row of CSV.
	 * @param row the row
	 * @return the leaderboard entry
	 */
	public static GameResult fromCsvRow(String row) {
		String[] parts = row.split(",");
		if (parts.length != 4)
			throw new IllegalArgumentException("Invalid CSV row: not exactly 4 fields");
		
		// create the object based on the provided parts
		// (name, is AI smart, turn count, win time)
		return new GameResult(
			parts[0], 
			Boolean.parseBoolean(parts[1]),
			Integer.parseInt(parts[2]), 
			Utilities.stringToMillis(parts[3]));
	}
	
	/**
	 * Converts a leaderboard entry to a row of CSV.
	 * @return a row of CSV for the leaderboard entry.
	 */
	public String toCsvRow() {
		return String.format("%s,%s,%s,%s", name, isAISmart, turnCount, Utilities.millisToString(winTime));
	}
	
	/**
	 * Gets the name for this leaderboard entry
	 * @return the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Gets whether the game was played against smart AI.
	 * @return true if the AI is smart, false if not
	 */
	public boolean getIsAISmart() {
		return isAISmart;
	}
	/**
	 * Gets the number of turns taken for this leaderboard entry.
	 * @return the number of turns
	 */
	public int getTurnCount() {
		return turnCount;
	}
	
	public long getWinTime() {
		return winTime;
	}
}
