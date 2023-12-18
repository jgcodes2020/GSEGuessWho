package ca.gse.guesswho;

import java.io.IOException;

import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;

public class GuessWho {
    public static void main(String[] args) throws IOException {
		GameState.loadCharacters(GuessWho.class.getResource("models/characters.csv"));
		
		for (GuessWhoCharacter character : GameState.getCharacterList()) {
			System.out.println(character);
		}
    }
}