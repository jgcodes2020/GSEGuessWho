package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.components.GScrollConstrainedPanel;
import ca.gse.guesswho.events.*;
import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;
import ca.gse.guesswho.models.players.*;

/**
 * Panel that displays and manages the game's state.
 */
public class GamePanel extends JPanel {
	static final String CARD_QUESTION = "question";
	static final String CARD_ANSWER = "answer";

	private MainWindow main;
	private CardLayout cards;
	private GameState state;

	private JPanel boardPanel;
	private GameQuestionPanel questionPanel;
	private GameAnswerPanel answerPanel;

	private JPanel chatPanel;
	private JPanel chatPanelContent;

	// Consumer<GameWonEvent> represents a method that takes
	// GameWonEvent and returns void
	private ArrayList<Consumer<GameWonEvent>> gameWonHandlers = new ArrayList<>();

	/**
	 * Internal method. Creates a JPanel for the game board.
	 * 
	 * @return a JPanel for the game board.
	 */
	private JPanel buildBoard() {
		JPanel board = new JPanel();
		cards = new CardLayout();
		board.setLayout(cards);

		questionPanel = new GameQuestionPanel(this);
		answerPanel = new GameAnswerPanel(this);

		board.add(questionPanel, CARD_QUESTION);
		board.add(answerPanel, CARD_ANSWER);

		cards.show(board, CARD_QUESTION);
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
		chatPanelContent = new GScrollConstrainedPanel(true, false);
		chatPanelContent.setLayout(new GridLayout(20, 1));// Need to fix the layout but i want things to go down.
		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

		board.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel questions = new JLabel("Questions");
		JScrollPane scroller = new JScrollPane(chatPanelContent);
		chatPanelContent.add(questions);

		board.add(scroller);

		board.setMinimumSize(new Dimension(900, 0));
		return board;
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
		chatPanelContent.add(questions);

	}

	/**
	 * Called to switch to the win screen. Subpanels only have access to
	 * direct parents so we have to chain upwards.
	 * @param winnerIsP1 If true, signal that the winner was player 1.
	 */
	void showWinScreen(boolean winnerIsP1) {
		main.showWinScreen(winnerIsP1);
	}

	/**
	 * Constructs a GamePanel for the provided players.
	 * @param mainWindow the main window to link this game panel to
	 * @param p1 the first player
	 * @param p2 the second player
	 */
	public GamePanel(MainWindow mainWindow, Player p1, Player p2) {
		main = mainWindow;
		state = new GameState(p1, p2);

		boardPanel = buildBoard();
		chatPanel = buildChatboard();

		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(chatPanel, BorderLayout.EAST);
	}

	GameState getState() {
		return state;
	}


	private void switchGamePanel(String boardString) {
		cards.show(boardPanel, boardString);
	}
}
