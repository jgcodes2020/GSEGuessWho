package ca.gse.guesswho.views;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.events.*;
import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;
import ca.gse.guesswho.models.players.*;

public class GamePanel extends JPanel {
	private GameState state;
	private JPanel boardPanel;
	private JPanel questionPanel;

	private CharacterCard[] cards;
	private ButtonGroup cardGroup;
	
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
		
		cardGroup = new ButtonGroup();

		for (int i = 0; i < characterAmt; i++) {
			GuessWhoCharacter character = DataCaches.getCharacterList().get(i);
			
			cards[i] = new CharacterCard(character);
			cards[i].addActionListener(this::characterCardPressed);
			cardGroup.add(cards[i]);
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
		userCharacter.setEnabled(false);
		
		for (String question : DataCaches.getQuestions().keySet()) {
			questions.add(question);
		}
		
		questionList = new JList<String>(questions.toArray(new String[0]));
		questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		questionList.addListSelectionListener(this::questionListValueChanged);
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

	private void submitButtonPressed(ActionEvent e) {
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
		addToResponse(state.getPlyQuestion()); // Put the question into the response panel
		
		state.doNextTurn();//Give the turn to the next person. (Assumed as AI)

		addToResponse(state.getAns());// Put the awnser into the response panel
		addToResponse(state.getPlyQuestion());

		currentWinner = state.getWinner();
		// check if the player won.
		if (currentWinner != GameState.WINNER_NONE)
			fireGameWon(currentWinner == GameState.WINNER_P1);

		if (!state.getCurrentPlayer().isHuman()) {
			state.doNextTurn();
			addToResponse(state.getAns());

			
			// check the winner again
			currentWinner = state.getWinner();
			if (currentWinner != GameState.WINNER_NONE)
				fireGameWon(currentWinner == GameState.WINNER_P1);
		}
		questionList.clearSelection();


		updateUIState();
		boardPanel.repaint();
		this.validate();
	}
	
	private void characterCardPressed(ActionEvent e) {
		// if we select a character, then deselect any question
		// we might have selected
		questionList.clearSelection();
	}
	
	private void questionListValueChanged(ListSelectionEvent e) {
		// if we select a question, then deselect any character
		// we might have selected
		if (!questionList.getSelectionModel().isSelectionEmpty()) {
			cardGroup.clearSelection();
		}
	}
	
	public void addToResponse(String response) {
		JLabel questions = new JLabel();
		questions.setText(response +" | "+state.getCurrentPlayer().getName());
		if (state.getPlayer1Turn() == true) {
			questions.setHorizontalAlignment(SwingConstants.LEFT);
		} else {
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
