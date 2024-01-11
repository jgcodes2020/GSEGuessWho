package ca.gse.guesswho.views;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.history.GameHistory;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.models.players.SmartAIPlayer;

/**
 * Frame for the main window, manages transitions between panels.
 */
public class MainWindow extends JFrame {
	static final String CARD_MENU = "menu";
	static final String CARD_AI_SETUP = "aisetup";
	static final String CARD_GAME = "game";
	static final String CARD_WIN_SCREEN = "winScreen";
	static final String CARD_ANSWER = "answer";
	static final String CARD_TUTORIAL = "tutorial";
	static final String CARD_CREDIT = "credit";
	static final String CARD_PVP_SETUP = "pvpsetup";
	static final String CARD_SWITCH_CONFIRM = "switch";

	private static final String SMART_AI_NAME = "John";
	private static final String DUMB_AI_NAME = "Gina";
	// UI ELEMENTS HERE
	// ----------------
	// thesse can be modified by the other view classes.
	
	JPanel rootPanel = null;
	CardLayout rootLayout = null;
	
	GameAISetupPanel aiSetupPanel = null;
	TutorialPanel tutorialPanel = null;
	MenuPanel menuPanel = null;
	GamePanel gamePanel = null;
	WinScreenPanel winPanel = null;
	CreditPanel creditPanel = null;
	GamePvpSetupPanel pvpSetupPanel = null;
	SwitchConfirmPanel switchConfirmPanel = null;
	
	/**
	 * Constructs a new main window.
	 */
	public MainWindow() {
		setTitle("Guess Who????????????????????");
		setSize(1440, 1024);
		
		
		// CardLayout lets us switch beetween various panels
		// programatically.
		rootLayout = new CardLayout();
		
		rootPanel = new JPanel();
		rootPanel.setLayout(rootLayout);
		
		// create panels that can be pre-built and reused
		menuPanel = new MenuPanel(this);
		rootPanel.add(menuPanel, CARD_MENU);

		winPanel = new WinScreenPanel(this);
		rootPanel.add(winPanel, CARD_WIN_SCREEN);

		tutorialPanel = new TutorialPanel(this);
		rootPanel.add(tutorialPanel, CARD_TUTORIAL);

		aiSetupPanel = new GameAISetupPanel(this);
		rootPanel.add(aiSetupPanel, CARD_AI_SETUP);

		pvpSetupPanel = new GamePvpSetupPanel(this);
		rootPanel.add(pvpSetupPanel, CARD_PVP_SETUP);

		switchConfirmPanel = new SwitchConfirmPanel(this);
		rootPanel.add(switchConfirmPanel, CARD_SWITCH_CONFIRM);

		creditPanel = new CreditPanel(this);
		rootPanel.add(creditPanel,CARD_CREDIT);


		setContentPane(rootPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * A method for creating and showing panels required to start game
	 * 
	 * 
	 * @param playerName Name of player in String
	 * @param isFirst	A boolean variable for whether P1 is the first player
	 * @param isAISmart A boolean variable for whether the AI should be smart(hard)
	 */
	void createGameAI(String playerName, boolean isFirst, boolean isAISmart) {
		// setup human player

		Player player1 = new HumanPlayer(playerName);
		// setup AI player (names are hardcoded)
		Player player2;
		if (isAISmart)
			player2 = new SmartAIPlayer(SMART_AI_NAME, DataCaches.randomCharacter());
		else
			player2 = new DumbAIPlayer(DUMB_AI_NAME, DataCaches.randomCharacter());
		gamePanel = new GamePanel(this, player1, player2, isFirst);
		// switch to game panel
		rootPanel.add(gamePanel, MainWindow.CARD_GAME);
		rootLayout.show(rootPanel, MainWindow.CARD_GAME);
	}

	void createGamePvp(String p1Name, String p2Name, boolean isFirst){
		Player player1 = new HumanPlayer(p1Name);
		// setup AI player (names are hardcoded)
		Player player2 = new HumanPlayer(p2Name);
		gamePanel = new GamePanel(this, player1, player2, isFirst);
		// switch to game panel
		rootPanel.add(gamePanel, MainWindow.CARD_GAME);
		switchPanel("switch");
		switchConfirmPanel.setText(p1Name, p2Name, isFirst);
	}




	/**
	 * Switches the screen to the win screen and make a GameWonEvent
	 * @param isWinnerP1 a variable representing whether the winner is Player 1
	 */
	void showWinScreen(boolean isWinnerP1, GameHistory history) {
		GameWonEvent event = new GameWonEvent(this, isWinnerP1,history);
		winPanel.updateView(event);
		switchPanel(CARD_WIN_SCREEN);
		System.out.println("yo");

	}

	/**
	 * Switches the screen to the win screen and make a GameWonEvent
	 * @param isWinnerP1 a variable representing whether the winner is Player 1
	 */
	void showWinScreen(boolean isWinnerP1, GameHistory history,GameState state) {
		GameWonEvent event = new GameWonEvent(this, isWinnerP1,history, state);
		winPanel.updateView(event);
		switchPanel(CARD_WIN_SCREEN);
		System.out.println("yo");
	}

	public void switchPanel(String panelString){
		rootLayout.show(rootPanel, panelString);
	}




}
