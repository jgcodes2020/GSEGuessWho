package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
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
	static final String CARD_SETUP = "setup";
	static final String CARD_GAME = "game";
	static final String CARD_WIN_SCREEN = "winScreen";
	static final String CARD_ANSWER = "answer";
	static final String CARD_TUTORIAL = "tutorial";
	static final String CARD_CREDIT = "credit";
	
	private static final String SMART_AI_NAME = "John";
	private static final String DUMB_AI_NAME = "Gina";
	// UI ELEMENTS HERE
	// ----------------
	// thesse can be modified by the other view classes.
	
	JPanel rootPanel = null;
	CardLayout rootLayout = null;
	
	GameSetupPanel setupPanel = null;
	TutorialPanel tutorialPanel = null;
	MenuPanel menuPanel = null;
	GamePanel gamePanel = null;
	WinScreenPanel winPanel = null;
	CreditPanel creditPanel = null;
	
	/**
	 * Constructs a new main window.
	 */
	public MainWindow() {
		setTitle("Guess Who Game");
		setSize(1440, 1024);

    /*setUndecorated(true); // remove the default window decorations

    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(Color.RED);
    titlePanel.setPreferredSize(new Dimension(getWidth(), 30));

     // Create minimize, maximize, and close buttons
    JButton minimizeButton = new JButton("-");
    minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED)); // Minimize the window

    JButton maximizeButton = new JButton("+");
    maximizeButton.addActionListener(e -> {
        if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        } else {
            setExtendedState(JFrame.NORMAL); // Restore the window
        }
    });

    JButton closeButton = new JButton("x");
    closeButton.addActionListener(e -> dispose()); // Close the window

    // Add buttons to the title panel
    titlePanel.add(minimizeButton);
    titlePanel.add(maximizeButton);
    titlePanel.add(closeButton);

    

    
    add(titlePanel, BorderLayout.NORTH);

    */
		
		
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

		setupPanel = new GameSetupPanel(this);
		rootPanel.add(setupPanel, CARD_SETUP);

		creditPanel = new CreditPanel(this);
		rootPanel.add(creditPanel,CARD_CREDIT);


		setContentPane(rootPanel);

    //add(rootPanel, BorderLayout.CENTER);
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
	void createGame(String playerName, boolean isFirst, boolean isAISmart) {
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


	/**
	 * Switches the screen to the win screen and make a GameWonEvent
	 * @param isWinnerP1 a variable representing whether the winner is Player 1
	 */
	void showWinScreen(boolean isWinnerP1, GameHistory history) {
		GameWonEvent event = new GameWonEvent(this, isWinnerP1, history);
		winPanel.updateView(event);
		switchPanel(CARD_WIN_SCREEN);
	}

	public void switchPanel(String panelString){
		rootLayout.show(rootPanel, panelString);
	}




}
