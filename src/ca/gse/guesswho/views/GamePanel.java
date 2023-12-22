package ca.gse.guesswho.views;
//gui
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;

public class GamePanel extends JPanel {
	private GameState state;
	private CharacterCard[] cards;
	private JList<String> questionList;
	private JLabel errorMessage;

	private JPanel buildBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(4, 6));

		final int characterAmt = 24;
		cards = new CharacterCard[characterAmt];

		for (int i = 0; i < characterAmt; i++) {
			GuessWhoCharacter character = DataCaches.getCharacterList().get(i);

			CharacterCard curCard;
			curCard = new CharacterCard(character);
			cards[i] = curCard;
			board.add(cards[i]);
		}
		return board;
	}


	private JPanel questionBoard(){
		JPanel board = new JPanel();
		JPanel innerBoard = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
		JLabel questions = new JLabel ("questions");
		JScrollPane scroller = new JScrollPane(innerBoard);
		if (state.getPlayer1Turn()==true){
			questions.setText("a");

			questions.setHorizontalAlignment(SwingConstants.LEFT);
		}
		else{
			questions.setText("b");

			questions.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		innerBoard.add(questions);

		board.add(scroller);

		board.setMinimumSize(new Dimension(900, 0));
		return board;
	}

	private JPanel bottomBar(){
		JPanel board = new JPanel();
		board.setLayout(new FlowLayout());
		ArrayList <String> questions = new ArrayList<String>();
		JButton confirmButton = new JButton("Confirm");
		errorMessage = new JLabel("");
		CharacterCard userCharacter = new CharacterCard(state.getCurrentPlayer().getSecretCharacter());
		for (String question : DataCaches.getQuestions().keySet()) {
			questions.add(question);
		}
		questionList = new JList<String>(questions.toArray(new String[questions.size()]));
		JScrollPane questionScroll = new JScrollPane(questionList);

		questionScroll.setViewportView(questionList);
		//board.setPreferredSize(new Dimension(Integer.MAX_VALUE, 210));;//720 by 210
		confirmButton.addActionListener(this::submitButtonPressed);
		ActionListener s;
		board.add(questionScroll);
		board.add(confirmButton);
		board.add(userCharacter);
		board.add(errorMessage);
		return board;

	}

	private boolean checkScrollSelection(){
		return questionList.getSelectedIndex() != -1;
	}

	public void submitButtonPressed(ActionEvent e){
		if (checkScrollSelection()) {
			errorMessage.setText("");
		}
		else{
			errorMessage.setText("You have to select a question");
		}
	}
	
	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);


		setLayout(new BorderLayout());
		add(buildBoard(), BorderLayout.CENTER);
		add(bottomBar(), BorderLayout.SOUTH);
		add(questionBoard(), BorderLayout.EAST);

	}
}
