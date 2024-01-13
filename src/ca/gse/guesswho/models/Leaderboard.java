/*
Leaderboard.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

/**
 * Class representing the leaderboard.
 */
public class Leaderboard extends AbstractTableModel {

	/**
	 * Comparator class representing the ordering of scores on the leaderboard.
	 */
	private static class LeaderboardOrder implements Comparator<GameResult> {
		/**
		 * Compares two game results in leaderboard order. Games are first compared by
		 * turn count. If they have the same turn count they will be compared by time.
		 * 
		 * @return the value 0 if {@code x == y}; a value less than 0 if {@code x < y};
		 *         and a value greater than 0 if {@code x > y}
		 */
		@Override
		public int compare(GameResult x, GameResult y) {
			// sort first by turn count
			int cmpResult = Integer.compare(x.getTurnCount(), y.getTurnCount());
			if (cmpResult != 0)
				return cmpResult;
			// then by win time
			return Long.compare(x.getWinTime(), y.getWinTime());
		}
	}

	private static final LeaderboardOrder SCORE_ORDER = new LeaderboardOrder();

	private File path;
	private ArrayList<GameResult> results;

	/**
	 * Constructs a new leaderboard from a file.
	 * 
	 * @param path the path to the leaderboard file
	 * @throws IOException if an I/O error occurs
	 */
	public Leaderboard(File path) throws IOException {
		this.path = path;
		// load the current leaderboard scores
		results = new ArrayList<>();
		if (path.exists())
			load();
	}

	/**
	 * Gets the file used for this leaderboard
	 * 
	 * @return the file used for this leaderboard.
	 */
	public File getPath() {
		return path;
	}

	/**
	 * Adds an entry to the leaderboard.
	 * 
	 * @param result the game result to add
	 */
	public void addEntry(GameResult result) {
		// insert the new result so that it's sorted
		int lastIndex = Utilities.insertIntoSorted(results, result, SCORE_ORDER);
		// let the model know there's a new row
		this.fireTableRowsInserted(lastIndex, lastIndex);
	}

	/**
	 * Loads the leaderboard from the file.
	 * 
	 * @throws IOException If an I/O error occurs.
	 */
	public void load() throws IOException {
		// clear all elements, then signal the event if needed
		int sizeBeforeDelete = results.size();
		results.clear();
		if (sizeBeforeDelete > 0)
			this.fireTableRowsDeleted(0, sizeBeforeDelete - 1);

		// load all leaderboard entries
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				results.add(GameResult.fromCsvRow(line));
			}
		}
		// sort the results according to leaderboard order.
		Utilities.sort(results, SCORE_ORDER);
		// let the model know that we loaded everything
		this.fireTableRowsInserted(0, results.size() - 1);
	}

	/**
	 * Saves the leaderboard to the file.
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	public void save() throws IOException {
		// write out all CSV rows
		try (PrintWriter pw = new PrintWriter(path)) {
			for (GameResult result : results) {
				pw.println(result.toCsvRow());
			}
		}
	}

	/**
	 * Gets the number of columns in this table
	 * 
	 * @return 4 (as there are 4 fields in {@link GameResult})
	 */
	@Override
	public int getColumnCount() {
		return 4;
	}

	/**
	 * Gets the number of rows in this table.
	 * 
	 * @return the number of game results on the leaderboard
	 */
	@Override
	public int getRowCount() {
		return results.size();
	}

	/**
	 * Gets a value at a particular location in the table.
	 * 
	 * @param rowIndex    the row index
	 * @param columnIndex the column index
	 * @return the value in the table located on the {@code rowIndex}th row and
	 *         {@code columnIndex}th column.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// if out of bounds, don't throw an exception, return null
		if (rowIndex >= results.size())
			return null;
		//
		GameResult row = results.get(rowIndex);

		switch (columnIndex) {
			case 0:
				return row.getName();
			case 1:
				return row.getIsAISmart();
			case 2:
				return row.getTurnCount();
			case 3:
				return Utilities.millisToString(row.getWinTime());
			default:
				throw new IndexOutOfBoundsException("Column index " + columnIndex + " out of bounds!");
		}
	}

	/**
	 * Gets the column names for a given column.
	 * 
	 * @param column the column index
	 * @return the column name
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Name";
			case 1:
				return "AI Smart?";
			case 2:
				return "Turn Count";
			case 3:
				return "Time Taken";
			default:
				throw new IndexOutOfBoundsException("Column index " + column + " out of bounds!");
		}
	}

	/**
	 * Gets the data type of a given column, enabling it to be displayed better.
	 * 
	 * @param column the column
	 * @return the data type of the column.
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			case 2:
				return Integer.class;
			case 3:
				return String.class;
			default:
				throw new IndexOutOfBoundsException("Column index " + column + " out of bounds!");
		}
	}
}
