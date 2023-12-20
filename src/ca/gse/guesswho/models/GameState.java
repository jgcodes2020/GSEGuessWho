package ca.gse.guesswho.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameState {
    private static List<GuessWhoCharacter> characterList = null;
    private static Random rng = new Random();

    private Player player1;
    private Player player2;

    private byte winner;
    private boolean isPlayer1Turn;

    public static final byte WINNER_NONE = 0;
    public static final byte WINNER_P1 = 1;
    public static final byte WINNER_P2 = 2;

    /**
     * Loads the character list from a CSV file. This must be done before creating
     * any
     * {@link GameState} instances.
     * 
     * @param path the path to load from.
     * @throws IOException if an I/O error occurs.
     */
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
            // convert the character list to an unmodifiable list.
            characterList = Collections.unmodifiableList(characters);
        }
    }

    /**
     * Gets the global character list.
     * @return the global character list, or null if it hasn't been loaded
     */
    public static List<GuessWhoCharacter> getCharacterList() {
        return characterList;
    }

    /**
     * Creates an instance of a Guess Who game with two players. The first player
     * goes first.
     * 
     * @param player1 the first player
     * @param player2 the second player
     */
    public GameState(Player player1, Player player2) {
        if (characterList == null) {
            throw new IllegalStateException("Character list must be loaded before starting a game!");
        }

        this.player1 = player1;
        this.player2 = player2;

        winner = WINNER_NONE;
        isPlayer1Turn = true;

        // draw a random card for the players who don't have one yet
        if (this.player1.getSecretIndex() < 0) {
            this.player1.setSecretIndex(rng.nextInt(characterList.size()));
        }
        if (this.player2.getSecretIndex() < 0) {
            this.player2.setSecretIndex(rng.nextInt(characterList.size()));
        }
    }
	
	/**
	 * Gets the current winner of the game.
	 * @return the game's winner
	 * @see GameState#WINNER_NONE
	 * @see GameState#WINNER_P1
	 * @see GameState#WINNER_P2
	 */
	public byte getWinner() {
		return winner;
	}

    /**
     * Performs the next player's turn.
     */
    public void doNextTurn() {
        // determine who is asking a question and who is answering the question
        Player asking, answering;
        if (this.isPlayer1Turn) {
            asking = player1;
            answering = player2;
        } else {
            asking = player2;
            answering = player1;
        }

        Question question = asking.takeTurn();
        GuessWhoCharacter secretCharacter = characterList.get(answering.getSecretIndex());
        boolean matchValue = question.match(secretCharacter);

        if (question.getIsFinal()) {
            // player 1 wins if it is their turn and the character matches, or if it is the
            // other person's turn
            // and the character doesn't match. This is equivalent to A XNOR B or NOT (A XOR
            // B).
            boolean isP1Winner = !(this.isPlayer1Turn ^ matchValue);
            winner = isP1Winner ? WINNER_P1 : WINNER_P2;
        } else {
            BitSet remainingIndexes = asking.getRemainingIndexes();
            for (int i = 0; i < remainingIndexes.length(); i++) {
                // if the current character doesn't match the same as the secret character, flip it down
                if (question.match(characterList.get(i)) != matchValue)
                    remainingIndexes.clear(i);
            }
        }
        // switch turns
        this.isPlayer1Turn = !this.isPlayer1Turn;
    }
}
