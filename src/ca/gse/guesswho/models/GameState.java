package ca.gse.guesswho.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
			// TODO: add character reading logic
		}
	}
	
	public static GuessWhoCharacter[] getCharacterList() {
		return characterList;
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
		// TODO: add turn logic
	}
}
