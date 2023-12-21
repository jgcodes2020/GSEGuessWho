package ca.gse.guesswho.views;
//gui
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JPanel;
import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;

public class GamePanel extends JPanel {
	private GameState state;
	private CharacterCard[] cards;

	private JPanel buildBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(4, 6));

		final int characterAmt = 24;
		cards = new CharacterCard[characterAmt];

		for (int i = 0; i < characterAmt; i++) {
			GuessWhoCharacter character = DataCaches.getCharacterList().get(i);
			Image img = DataCaches.getImageCache().get(i);

			CharacterCard curCard;
			curCard = new CharacterCard(character.getName(), img);
			cards[i] = curCard;
			board.add(cards[i]);
		}
		return board;
	}

	private JPanel bottomBar(){
		JPanel board = new JPanel();
		board.setLayout(new FlowLayout());
		ArrayList <String> questions = new ArrayList<String>();
		JButton confirmButton = new JButton("Confirm");
		CharacterCard userCharacter = new CharacterCard("Rachel", null);
		for (String question : DataCaches.getQuestions().keySet()) {
			questions.add(question);
		}
		JList<String> questionList = new JList<String>(questions.toArray(new String[questions.size()]));
		JScrollPane questionScroll = new JScrollPane(questionList);

		questionScroll.setViewportView(questionList);
		//board.setPreferredSize(new Dimension(Integer.MAX_VALUE, 210));;//720 by 210


		board.add(questionScroll);
		board.add(confirmButton);
		board.add(userCharacter);
		return board;

	}
	
	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);


		setLayout(new BorderLayout());
		add(buildBoard(), BorderLayout.CENTER);
		add(bottomBar(), BorderLayout.SOUTH);

	}
}
