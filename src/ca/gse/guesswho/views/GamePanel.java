package ca.gse.guesswho.views;
//gui
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.*;
import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.questions.AttributeQuestion;

public class GamePanel extends JPanel {
	private GameState state;
	private CharacterCard[] cards;
	
	private JPanel buildBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(4, 6));
		
		final int characterAmt = 24;
		cards = new CharacterCard[characterAmt];

		for (int i = 0; i < characterAmt; i++){
			GuessWhoCharacter character = GameState.getCharacterList().get(i);
			
			CharacterCard curCard = new CharacterCard(character.getName(),null);
			cards[i] = curCard;
			board.add(cards[i]);
		}
		return board;
	}

	private JPanel buildInput(){
		JPanel board = new JPanel();
		board.setLayout(new FlowLayout());
		ArrayList <String> questions = new ArrayList<String>();
		JButton confirmButton = new JButton("Confirm");
		for (String question : QuestionBank.getQuestions().keySet()) {
			questions.add(question);
		}
		JList<String> questionList = new JList<String>(questions);

		board.add(questionList);
		board.add(confirmButton);
		return board;

	}
	
	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);
		
		setLayout(new BorderLayout());
		add(buildBoard(), BorderLayout.CENTER);
		add(buildInput(), BorderLayout.SOUTH);

	}
}
