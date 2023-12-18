package ca.gse.guesswho.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameState {
	private static GuessWhoCharacter[] characterList = null;
	
	private Player player1;
	private Player player2;
	
	private byte winner;
	private boolean isPlayer1Turn;
	
	public static final byte WINNER_NONE = 0;
	public static final byte WINNER_P1 = 1;
	public static final byte WINNER_P2 = 2;
	
	public static void loadCharacters(URL path) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(path.openStream()))) {
			// this stores the last line we read
			String line;
			// this keeps track of the characters we've parsed
			ArrayList<GuessWhoCharacter> characters = new ArrayList<>();
			// read the header row. We could validate it, but I'm not doing that now.
			line = br.readLine();
			if (line == null)
				throw new IllegalArgumentException("Parsing failed! (missing header row)");
			// read a line, if it isn't the end of the file, parse and add it
			// rinse and repeat until br.readLine() returns null (i.e. end of file)
			while ((line = br.readLine()) != null) {
				characters.add(GuessWhoCharacter.fromCsvRow(line));
			}
			// convert the character list to an array.
			characterList = characters.toArray(new GuessWhoCharacter[0]);
		}
	}
	
	/**
	 * Gets the global character list. This is an array for efficiency; it should
	 * NOT under any circumstances be modified.
	 * @return the global character list.
	 */
	public static List<GuessWhoCharacter> getCharacterList() {
		return Collections.unmodifiableList(Arrays.asList(characterList));
	}
	
	public GameState(Player player1, Player player2) {
		if (characterList == null) {
			throw new IllegalStateException("Character list must be loaded before starting a game!");
		}
		
		this.player1 = player1;
		this.player2 = player2;
		
		winner = GameState.WINNER_NONE;
		isPlayer1Turn = true;
	}
	
	public void doNextTurn() {
		
	}
}
