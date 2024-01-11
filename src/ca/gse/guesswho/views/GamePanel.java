package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.Timer;
import ca.gse.guesswho.components.GScrollConstrainedPanel;
import ca.gse.guesswho.events.*;
import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.history.GameHistory;
import ca.gse.guesswho.models.history.GameHistoryEntry;
import ca.gse.guesswho.models.players.AIPlayer;
import ca.gse.guesswho.models.questions.CharacterQuestion;

/**
 * Panel that displays and manages the game's state.
 */
public class GamePanel extends JPanel {
	static final String CARD_QUESTION = "question";
	static final String CARD_ANSWER = "answer";

	private MainWindow main;
	private CardLayout cards;
	private GameState state;
	private GameHistory history;

	private JPanel boardPanel;
	private GameQuestionPanel questionPanel;
	private GameAnswerPanel answerPanel;
	private JPanel topBarPanel;
	private JPanel chatPanel;
	private JPanel chatPanelContent;

	private long startTime;
	private Timer timer;
	private JLabel timeLabel;

	private JLabel roundLabel;

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
		JPanel textBoard = new JPanel();
		JPanel board = new JPanel();

		chatPanelContent = new GScrollConstrainedPanel(true, false);
		chatPanelContent.setLayout(new BoxLayout(chatPanelContent, BoxLayout.Y_AXIS));// Need to fix the layout but i
																						// want things to go down.
		board.setLayout(new BorderLayout());

		board.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		timeLabel = new JLabel("Time: 00:00:00");
		textBoard.add(timeLabel, BorderLayout.WEST);

		roundLabel = new JLabel("Turn " + state.getTurnCount());
		textBoard.add(roundLabel, BorderLayout.EAST);

		board.add(textBoard, BorderLayout.NORTH);
		JLabel questions = new JLabel("Questions");
		JScrollPane scroller = new JScrollPane(chatPanelContent);

		chatPanelContent.add(questions, BorderLayout.NORTH);
		board.add(scroller, BorderLayout.CENTER);

		board.setMinimumSize(new Dimension(350, 0));
		board.setPreferredSize(new Dimension(350, 100));
		return board;
	}

	/**
	 * Adds a message to the chatboard.
	 * 
	 * @param response The string to add to the chatboard.
	 */
	public void addChatMessage(String response, boolean isPlayer1Turn, String name) {
		JLabel questions = new JLabel();
		questions.setText(response + " | " + name);
		questions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		System.out.println(isPlayer1Turn);

		if (isPlayer1Turn) {
			questions.setHorizontalAlignment(JLabel.LEFT);
		} else {
			questions.setHorizontalAlignment(JLabel.RIGHT);
		}
		chatPanelContent.add(questions);
	}

	/**
	 * Runs all pending AI phases, then switches to the correct panel
	 * for the next player to respond.
	 */
	public void runAITurnsAndSwitchPanel() {
		while (!state.getCurrentPlayer().isHuman()) {
			if (runOneTurn()) {
				return;
			}
		}
		if (state.getIsAnswerPhase()) {
			doSwitcher();
			switchGamePanel(CARD_ANSWER);
		} else {
			switchGamePanel(CARD_QUESTION);
		}

	}

	/**
	 * Runs a single phase of the next turn, pumping a message to chat.
	 * 
	 * @return true if someone won on this turn.
	 */
	public boolean runOneTurn() {
		roundUpdate();
		boolean isAnswer = state.getIsAnswerPhase();
		boolean isPlayer1 = state.getPlayer1Turn();
		String name = state.getCurrentPlayer().getName();

		state.doNextPhase();

		// setup the answer, push history
		String message;
		if (isAnswer) {
			boolean answer = state.getLastAnswer();
			history.push(answer);
			// set chat message
			if (answer)
				message = "Yes";
			else
				message = "No";
		} else {
			Question question = state.getLastQuestion();
			history.push(question);
			// set chat message
			message = DataCaches.getQuestionString(question);
		}
		addChatMessage(message, isPlayer1, name);
		roundUpdate();
		// check for winner
		if (checkForWinner())
			return true;

		return false;
	}

	/**
	 * Checks if someone wins and triggers the win screen.
	 * 
	 * @return Whether somebody won.
	 */
	boolean checkForWinner() {
		// if nobody wins, exit now
		if (state.getWinner() == GameState.WINNER_NONE)
			return false;

		// update winner in history
		boolean isWinnerP1 = state.getWinner() == GameState.WINNER_P1;
		history.setIsWinnerP1(isWinnerP1);
		// check everyone's secret characters
		inferSecretCharacters(false);
		// show the win screen.
		main.showWinScreen(history);
		return true;
	}

	/**
	 * Causes the current player to instantly lose.
	 */
	void forfeit() {
		// If it's player 1's turn they should lose, i.e. player 2 should win, vice
		// versa.

		history.setIsWinnerP1(!state.getPlayer1Turn());
		// infer any secret characters. The game did not end properly, so we can't
		// use Character Questions.
		inferSecretCharacters(true);
		// show the win screen
		main.showWinScreen(history);
	}

	private void inferSecretCharacters(boolean isForfeit) {
		boolean isWinnerP1 = state.getWinner() == GameState.WINNER_P1;
		Player player1 = state.getPlayer1();
		Player player2 = state.getPlayer2();
		// Assuming the last question is a CharacterQuesetion, check if someone's secret
		// can be found
		if (!isForfeit) {
			List<GameHistoryEntry> entries = history.getEntries();
			GameHistoryEntry lastEntry = entries.get(entries.size() - 1);
			if (lastEntry.getAnswer()) {
				CharacterQuestion lastQuestion = (CharacterQuestion) lastEntry.getQuestion();
				if (isWinnerP1) {
					history.setP2Secret(lastQuestion.getCharacter());
				} else {
					history.setP1Secret(lastQuestion.getCharacter());
				}
			}
		}

		System.out.print(!player1.isHuman() + " a " + history.getP1Secret());

		// Set secrets for all AI players
		if (!player1.isHuman() && history.getP1Secret() == null) {
			// set the secret character for this AI
			System.out.println("P1 is an AI");
			AIPlayer aiPlayer1 = (AIPlayer) player1;
			history.setP1Secret(aiPlayer1.getSecret());
		}
		if (!player2.isHuman() && history.getP2Secret() == null) {
			// set the secret character for this AI
			System.out.println("P2 is an AI");
			AIPlayer aiPlayer2 = (AIPlayer) player2;
			history.setP2Secret(aiPlayer2.getSecret());
		}
	}

	/**
	 * Constructs a GamePanel for the provided players.
	 * 
	 * @param mainWindow the main window to link this game panel to
	 * @param p1         the first player
	 * @param p2         the second player
	 * @param isP1First  if true, player 1 goes first, otherwise player 2 goes first
	 */
	public GamePanel(MainWindow mainWindow, Player p1, Player p2, boolean isP1First) {
		main = mainWindow;
		// start the timer
		startTime = System.currentTimeMillis();
		timer = new Timer(1, this::timeUpdate);
		timer.setInitialDelay(1);
		timer.start();
		// initialize game state
		state = new GameState(p1, p2, isP1First);
		history = new GameHistory(p1.getName(), p2.getName(), isP1First);

		state = new GameState(p1, p2, isP1First);
		boardPanel = buildBoard();
		chatPanel = buildChatboard();
		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(chatPanel, BorderLayout.EAST);
		this.runAITurnsAndSwitchPanel();
	}

	/**
	 * Gets the current game state. This should only be used by other view classes,
	 * hence default access.
	 * 
	 * @return the game state.
	 */
	GameState getState() {
		return state;
	}

	private void roundUpdate() {
		roundLabel.setText("Turn " + (state.getTurnCount()));
	}

	private void timeUpdate(ActionEvent e) {
		timeLabel.setText("Time: " + Utilities.millisToString(System.currentTimeMillis() - startTime));
	}

	public long getRunTime() {
		return (System.currentTimeMillis() - startTime);
	}

	private void doSwitcher() {
		if (state.getPlayer1().isHuman() == true && state.getPlayer2().isHuman() == true) {
			main.showSwitchScreen(state.getPlayer1().getName(), state.getPlayer2().getName(), state.getPlayer1Turn());
		} else {
			return;
		}
	}

	/**
	 * INTERNAL: switches to a different card on the board panel.
	 * 
	 * @param boardString the board to switch to
	 */
	protected void switchGamePanel(String boardString) {
		cards.show(boardPanel, boardString);
	}

}
