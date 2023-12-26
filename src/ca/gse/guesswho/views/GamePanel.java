package ca.gse.guesswho.views;

//gui
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.*;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.models.questions.AttributeQuestion;

public class GamePanel extends JPanel {
	private GameState state;
	private JPanel boardPanel;
	private JPanel questionPanel;

	private CharacterCard[] cards;
	private JList<String> questionList;
	private JLabel errorMessage;
	JPanel innerBoard;

	// Consumer<GameWonEvent> represents a method that takes
	// GameWonEvent and returns void
	public ArrayList<Consumer<GameWonEvent>> gameWonHandlers;

	private JPanel buildBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(4, 6));
		// board.setBackground(Color.RED);
		// board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		final int characterAmt = 24;
		cards = new CharacterCard[characterAmt];
		
		// this merely needs to exist, we don't do anything
		// other than adding the cards to it
		ButtonGroup buttonGroup = new ButtonGroup();

		for (int i = 0; i < characterAmt; i++) {
			GuessWhoCharacter character = DataCaches.getCharacterList().get(i);
			
			cards[i] = new CharacterCard(character);
			buttonGroup.add(cards[i]);
			board.add(cards[i]);
		}
		return board;
	}

	private JPanel buildQuestionBoard() {
		JPanel board = new JPanel();
		innerBoard = new JPanel();
		innerBoard.setLayout(new GridLayout(20, 1));// Need to fix the layout but i want things to go down.
		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

		board.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel questions = new JLabel("Questions");
		JScrollPane scroller = new JScrollPane(innerBoard);
		innerBoard.add(questions);

		board.add(scroller);

		board.setMinimumSize(new Dimension(900, 0));
		return board;
	}

	private JPanel bottomBar() {
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.X_AXIS));
		ArrayList<String> questions = new ArrayList<String>();
		JButton confirmButton = new JButton("Confirm");
		errorMessage = new JLabel("");

		// board.setBackground(Color.RED);
		// board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		CharacterCard userCharacter = new CharacterCard(state.getCurrentPlayer().getSecretCharacter());
		userCharacter.setClickable(false);
		for (String question : DataCaches.getQuestions().keySet()) {
			questions.add(question);
		}
		questionList = new JList<String>(questions.toArray(new String[questions.size()]));
		JScrollPane questionScroll = new JScrollPane(questionList);

		questionScroll.setViewportView(questionList);
		questionScroll.setPreferredSize(new Dimension(540, 210));
		confirmButton.setPreferredSize(new Dimension(210, 210));
		userCharacter.setPreferredSize(new Dimension(320, 210));

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

	public void submitButtonPressed(ActionEvent e) {
		String questionSelected = questionList.getSelectedValue();
		if (!checkScrollSelection()) {
			errorMessage.setText("You have to select a question");
			return;
		}

		byte currentWinner;

		// set the current player's next question (it should be human)
		Map<String, AttributeQuestion> questionBank = DataCaches.getQuestions();
		AttributeQuestion nextQuestion = questionBank.get(questionSelected);
		((HumanPlayer) state.getCurrentPlayer()).setNextQuestion(nextQuestion);
		addToResponse(questionSelected);// Put the question into the response panel

		state.doNextTurn();
		addToResponse(state.getAns());// Put the awnser into the response panel

		currentWinner = state.getWinner();
		// check if the player won.
		if (currentWinner != GameState.WINNER_NONE)
			fireGameWon(currentWinner == GameState.WINNER_P1);

		if (!state.getCurrentPlayer().isHuman()) {
			state.doNextTurn();
			// check the winner again
			currentWinner = state.getWinner();
			if (currentWinner != GameState.WINNER_NONE)
				fireGameWon(currentWinner == GameState.WINNER_P1);
		}

		updateUIState();
		boardPanel.repaint();
		this.validate();
	}

	public void addToResponse(String response) {
		JLabel questions = new JLabel();
		if (state.getPlayer1Turn() == true) {
			questions.setText(response + " P1");

			questions.setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			questions.setText(response + " P2");

			questions.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		innerBoard.add(questions);

	}

	private void fireGameWon(boolean winnerIsP1) {
		for (Consumer<GameWonEvent> handler : gameWonHandlers) {
			handler.accept(new GameWonEvent(this, winnerIsP1));
		}
	}

	public void addGameWonListener(Consumer<GameWonEvent> handler) {
		gameWonHandlers.add(handler);
	}

	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);

		boardPanel = buildBoard();
		questionPanel = buildQuestionBoard();

		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(bottomBar(), BorderLayout.SOUTH);
		add(questionPanel, BorderLayout.EAST);

	}
}
