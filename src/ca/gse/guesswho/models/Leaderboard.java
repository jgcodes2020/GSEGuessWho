package ca.gse.guesswho.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class Leaderboard {

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
		load();
		
		// sort the results according to leaderboard order.
		Utilities.sort(results, SCORE_ORDER);
	}

	public File getPath() {
		return path;
	}

	public void addEntry(GameResult result) {
		Utilities.insertIntoSorted(results, result, SCORE_ORDER);
	}
	
	public void load() throws IOException {
		results.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
            while ((line = br.readLine()) != null) {
                results.add(GameResult.fromCsvRow(line));
            }
		}
		
		// sort the results according to leaderboard order.
		Utilities.sort(results, SCORE_ORDER);
	}
	
	public void save() throws IOException {
		try (PrintWriter pw = new PrintWriter(path)) {
			for (GameResult result : results) {
				pw.println(result.toCsvRow());
			}
		}
	}
}
