package ca.gse.guesswho;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.views.GamePanel;

public class GuessWho {
	private static GamePanel gamePanel;
	
	private static JFrame buildWindow() {
		JFrame frame = new JFrame("Guess Me");
		frame.setSize(640, 480);
		
		// temp: start the game now idk
		gamePanel = new GamePanel(new HumanPlayer("TestPlayer"), new DumbAIPlayer());
		frame.setContentPane(gamePanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
    public static void main(String[] args) throws IOException {
		GameState.loadCharacters(GuessWho.class.getResource("CharacterData.csv"));
		
		for (GuessWhoCharacter character : GameState.getCharacterList()) {
			System.out.println(character);
		}
		
		buildWindow().setVisible(true);
    }



}