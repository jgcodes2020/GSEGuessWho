package ca.gse.guesswho.views;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.events.*;
import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;
import ca.gse.guesswho.models.players.*;

/**
 * Panel that displays and manages the game's state.
 */
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

	/**
	 * Internal method. Creates a JPanel for the game board.
	 * 
	 * @return a JPanel for the game board.
	 */
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

	/**
	 * Internal method. Creates a JPanel for the question/answer
	 * chatboard.
	 * 
	 * @return a JPanel for the chatboard.
	 */
	private JPanel buildChatboard() {
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

	/**
	 * Internal method. Creates a JPanel for the bottom bar.
	 * 
	 * @return a JPanel for the bottom bar.
	 */
	private JPanel buildBottomBar() {
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

	/**
	 * Internal method. Updates the game board according to the
	 * current player's remaining indices.
	 */
	private void updateUIState() {
		BitSet playerRemainingIndexes = state.getCurrentPlayer().getRemainingIndexes();
		for (int i = 0; i < cards.length; i++) {
			cards[i].setCrossedOut(!playerRemainingIndexes.get(i));
		}
	}

	/**
	 * Internal method. Converted/passed as an {@link ActionListener} for
	 * the submit button on the bottom bar.
	 * 
	 * @param e the event being handled.
	 */
	private void submitButtonPressed(ActionEvent e) {
		if (questionList.getSelectedIndex() == -1) {
			errorMessage.setText("You have to select a question");
			return;
		}
		String questionSelected = questionList.getSelectedValue();

		byte currentWinner;

		// set the current player's next question (it should be human)
		Map<String, AttributeQuestion> questionBank = DataCaches.getQuestions();
		AttributeQuestion nextQuestion = questionBank.get(questionSelected);
		((HumanPlayer) state.getCurrentPlayer()).setNextQuestion(nextQuestion);
		// addToResponse(state.getLastQuestion()); // Put the question into the response
		// panel

		state.doNextTurn();// Give the turn to the next person. (Assumed as AI)

		// addToResponse(state.getLastAnswer());// Put the awnser into the response
		// panel
		// addToResponse(state.getLastQuestion());

		currentWinner = state.getWinner();
		// check if the player won.
		if (currentWinner != GameState.WINNER_NONE)
			fireGameWon(currentWinner == GameState.WINNER_P1);

		if (!state.getCurrentPlayer().isHuman()) {
			state.doNextTurn();
			// addToResponse(state.getLastAnswer());

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

	/**
	 * Internal method. Converted/passed as an {@link ActionListener} for
	 * the submit button on the bottom bar.
	 * 
	 * @param e the event being handled.
	 */
	private void characterCardPressed(ActionEvent e) {
		// if we select a character, then deselect any question
		// we might have selected
		questionList.clearSelection();
	}

	/**
	 * Internal method. Converted/passed as a {@link ListSelectionListener} for
	 * the question list in the bottom bar.
	 * 
	 * @param e
	 */
	private void questionListValueChanged(ListSelectionEvent e) {
		// if we select a question, then deselect any character
		// we might have selected
		if (!questionList.getSelectionModel().isSelectionEmpty()) {
			cardGroup.clearSelection();
		}
	}

	/**
	 * Internal method. Adds a message to the chatboard.
	 * 
	 * @param response The string to add to the chatboard.
	 * @implNote TODO: make this look nicer; refactor to be less dependent on
	 *           internal state
	 */
	public void addToResponse(String response) {
		JLabel questions = new JLabel();
		questions.setText(response + " | " + state.getCurrentPlayer().getName());
		if (state.getPlayer1Turn() == true) {
			questions.setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			questions.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		innerBoard.add(questions);

	}

	/**
	 * Internal method. Fires a GameWonEvent for this GamePanel.
	 * 
	 * @param winnerIsP1 If true, signal that the winner was player 1.
	 */
	private void fireGameWon(boolean winnerIsP1) {
		for (Consumer<GameWonEvent> handler : gameWonHandlers) {
			handler.accept(new GameWonEvent(this, winnerIsP1));
		}
	}

	/**
	 * Adds the specified mouse listener to receive "game won" events from this
	 * component. If listener {@code l} is {@code null}, no exception is thrown and
	 * no action is performed.
	 * 
	 * @param handler the "Game Won" event handler
	 */
	public void addGameWonListener(Consumer<GameWonEvent> handler) {
		addMouseListener(null);
		if (handler == null)
			return;
		gameWonHandlers.add(handler);
	}

	/**
	 * Constructs a GamePanel for the provided players.
	 * @param p1 the first player
	 * @param p2 the second player
	 */
	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);

		boardPanel = buildBoard();
		questionPanel = buildChatboard();

		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(buildBottomBar(), BorderLayout.SOUTH);
		add(questionPanel, BorderLayout.EAST);

	}
}
