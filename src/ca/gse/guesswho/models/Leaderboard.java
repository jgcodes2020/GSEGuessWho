package ca.gse.guesswho.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

public class Leaderboard extends AbstractTableModel {

	/**
	 * Comparator class representing the ordering of scores on the leaderboard.
	 */
	private static class LeaderboardOrder implements Comparator<GameResult> {
		
		@Override
		public int compare(GameResult o1, GameResult o2) {
			// sort first by turn count
			int cmpResult = Integer.compare(o1.getTurnCount(), o2.getTurnCount());
			if (cmpResult != 0)
				return cmpResult;
			// then by win time
			return Long.compare(o1.getWinTime(), o2.getWinTime());
		}
	}
	
	private static final LeaderboardOrder SCORE_ORDER = new LeaderboardOrder();
	
	private File path;
	private ArrayList<GameResult> results;

	public Leaderboard(File path) throws IOException {
		this.path = path;
		// load the current leaderboard scores
		results = new ArrayList<>();
		if (path.exists())
			load();
		
		// sort the results according to leaderboard order.
		Utilities.sort(results, SCORE_ORDER);
	}

	public File getPath() {
		return path;
	}

	public void addEntry(GameResult result) {
		Utilities.insertIntoSorted(results, result, SCORE_ORDER);
		// let the model know there's a new row
		int lastIndex = results.size() - 1;
		this.fireTableRowsInserted(lastIndex, lastIndex);
	}
	
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
		System.out.println("rows: " + results.size());
		// sort the results according to leaderboard order.
		Utilities.sort(results, SCORE_ORDER);
		// let the model know that we loaded everything
		this.fireTableRowsInserted(0, results.size() - 1);
		System.out.println("results");
	}
	
	public void save() throws IOException {
		try (PrintWriter pw = new PrintWriter(path)) {
			for (GameResult result : results) {
				pw.println(result.toCsvRow());
			}
		}
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return results.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= results.size())
			return null;
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
	 * Gets the column names for each column. 
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
	 * Gets the class of this column.
	 * @param column the column
	 * @return the class of the column.
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
				return Long.class;
			default:
				throw new IndexOutOfBoundsException("Column index " + column + " out of bounds!");
		}
	}
}
