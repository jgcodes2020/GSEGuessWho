package ca.gse.guesswho;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.swing.*;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.models.questions.AttributeQuestion;
import ca.gse.guesswho.views.GamePanel;
import ca.gse.guesswho.views.QuestionBank;

public class GuessWho {
	private static GamePanel gamePanel;
	
	private static JFrame buildWindow() {
		JFrame frame = new JFrame("Guess Me");
		frame.setSize(1440, 1024);
		
		// temp: start the game now idk
		gamePanel = new GamePanel(new HumanPlayer("TestPlayer"), new DumbAIPlayer());
		frame.setContentPane(gamePanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
    public static void main(String[] args) throws IOException {
		
		GameState.loadCharacters(GuessWho.class.getResource("CharacterData.csv"));
		QuestionBank.loadQuestions(GuessWho.class.getResource("QuestionBank.csv"));
		
		System.out.println("Characters:");
		for (GuessWhoCharacter character : GameState.getCharacterList()) {
			System.out.println(character);
		}
		System.out.println();
		
		System.out.println("Questions: ");
		for (Map.Entry<String, AttributeQuestion> entry : QuestionBank.getQuestions().entrySet()) {
			System.out.printf("%s (%s)\n", entry.getKey(), entry.getValue());
		}
		System.out.println();
		
		buildWindow().setVisible(true);
    }
}