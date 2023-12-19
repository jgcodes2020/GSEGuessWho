package ca.gse.guesswho;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.views.CharacterCard;

public class GuessWho {
	private static JFrame buildWindow() {
		JFrame frame = new JFrame("Guess Me");
		frame.setSize(640, 480);
		
		FlowLayout lineLayout = new FlowLayout();
		GridLayout gridLayout = new GridLayout(6,4);
		// add stuff
		JPanel boardPanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel responsePanel = new JPanel();

		inputPanel.setLayout(lineLayout);
		boardPanel.setLayout(gridLayout);
		buildBoard(boardPanel);
		// set a layout...
		// add stuff to contentPanel
		frame.setContentPane(boardPanel);
		//frame.setContentPane(contentPanel);
		return frame;
	}


	private static JPanel buildBoard(JPanel board){
		final int characterAmt = 24;
		CharacterCard[] characterCard = new CharacterCard[characterAmt];

		for (int i = 0; i < characterAmt;i++){
			CharacterCard yes = new CharacterCard(Integer.toString(i),null);
			characterCard[i] = yes;
			board.add(characterCard[i]);
		}
		return board;




	}
	
    public static void main(String[] args) throws IOException {
		GameState.loadCharacters(GuessWho.class.getResource("models/characters.csv"));
		
		for (GuessWhoCharacter character : GameState.getCharacterList()) {
			System.out.println(character);
		}
		
		buildWindow().setVisible(true);
    }



}