package ca.gse.guesswho.views;
//gui
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;

import javax.swing.*;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.models.questions.AttributeQuestion;

public class GamePanel extends JPanel {
	private GameState state;
	private JPanel boardPanel;
	
	private CharacterCard[] cards;
	private JList<String> questionList;
	private JLabel errorMessage;
	
	

	private JPanel buildBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(4, 6));
		board.setBackground(Color.RED);

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

	private JPanel bottomBar() {
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.X_AXIS));
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
		questionScroll.setPreferredSize(new Dimension(540, 210));;//720 by 210
		confirmButton.setPreferredSize(new Dimension(210, 210));;//720 by 210
		userCharacter.setPreferredSize(new Dimension(320, 210));;//720 by 210

		confirmButton.addActionListener(this::submitButtonPressed);
		
		board.add(Box.createHorizontalGlue());
		board.add(questionScroll);
		board.add(confirmButton);
		board.add(userCharacter);
		board.add(errorMessage);
		board.add(Box.createHorizontalGlue());
		return board;

	}

	private boolean checkScrollSelection() {
		return questionList.getSelectedIndex() != -1;
	}
	
	private void updateUIState() {
		BitSet playerRemainingIndexes = state.getCurrentPlayer().getRemainingIndexes();
		for (int i = 0; i < cards.length; i++) {
			cards[i].setCrossedOut(!playerRemainingIndexes.get(i));
		}
	}

	public void submitButtonPressed(ActionEvent e){
		if (!checkScrollSelection()) {
			errorMessage.setText("You have to select a question");
			return;
		}
		
		// set the current player's next question (it should be human)
		Map<String, AttributeQuestion> questionBank = DataCaches.getQuestions();
		AttributeQuestion nextQuestion = questionBank.get(questionList.getSelectedValue());
		((HumanPlayer) state.getCurrentPlayer()).setNextQuestion(nextQuestion);
		state.doNextTurn();
		
		if (!state.getCurrentPlayer().isHuman())
			state.doNextTurn();
		
		updateUIState();
		boardPanel.repaint();
	}
	
	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);
		
		boardPanel = buildBoard();

		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(bottomBar(), BorderLayout.SOUTH);
		add(questionBoard(), BorderLayout.EAST);

	}
}
