/*
MainWindow.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.Leaderboard;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.history.GameHistory;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.models.players.SmartAIPlayer;
import ca.gse.guesswho.sound.SoundEffects;

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
	static final String CARD_LEADERBOARD = "leaderboard";

	private static final String SMART_AI_NAME = "John";
	private static final String DUMB_AI_NAME = "Gina";
	// UI ELEMENTS HERE
	// ----------------
	// thesse can be modified by the other view classes.

	private JPanel rootPanel = null;
	private CardLayout rootLayout = null;

	private GameAISetupPanel aiSetupPanel = null;
	private TutorialPanel tutorialPanel = null;
	private MenuPanel menuPanel = null;
	private GamePanel gamePanel = null;
	private WinScreenPanel winPanel = null;
	private CreditPanel creditPanel = null;
	private GamePvpSetupPanel pvpSetupPanel = null;
	private SwitchConfirmPanel switchConfirmPanel = null;
	private LeaderboardPanel leaderboardPanel = null;

	// Panel-shared items
	private Leaderboard leaderboard;

	/**
	 * Constructs a new main window.
	 */
	public MainWindow(File leaderboardPath) {
		setTitle("Guess Who????????????????????");
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

		// setup the leaderboard
		try {
			leaderboard = new Leaderboard(leaderboardPath);
		} catch (IOException e) {
			// show a dialog to inform the user
			JOptionPane.showMessageDialog(null,
					"Could not load the leaderboard for some reason. The program will exit now.",
					"Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		// save the leaderboard when the window closes
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				saveLeaderboard();
				SoundEffects.unloadClips();
			}
		});

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
		rootPanel.add(creditPanel, CARD_CREDIT);

		leaderboardPanel = new LeaderboardPanel(this);
		rootPanel.add(leaderboardPanel, CARD_LEADERBOARD);

		setContentPane(rootPanel);

    //add(rootPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * A method for creating and showing panels required to start game
	 * 
	 * 
	 * @param playerName Name of player in String
	 * @param isFirst    A boolean variable for whether P1 is the first player
	 * @param isAISmart  A boolean variable for whether the AI should be smart(hard)
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

	void createGamePvp(String p1Name, String p2Name, boolean isFirst) {
		Player player1 = new HumanPlayer(p1Name);
		// setup AI player (names are hardcoded)
		Player player2 = new HumanPlayer(p2Name);
		gamePanel = new GamePanel(this, player1, player2, isFirst);
		// switch to game panel
		rootPanel.add(gamePanel, MainWindow.CARD_GAME);
		showSwitchScreen(p1Name, p2Name, isFirst);
	}

	/**
	 * Switches the screen to the win screen and make a GameWonEvent
	 * 
	 * @param isWinnerP1 a variable representing whether the winner is Player 1
	 */
	void showWinScreen(GameHistory history) {
		GameWonEvent event = new GameWonEvent(this, history);
		winPanel.updateView(event);
		switchPanel(CARD_WIN_SCREEN);
	}

	void showSwitchScreen(String p1Name, String p2Name, boolean isP1Turn) {
		switchPanel(CARD_SWITCH_CONFIRM);
		SoundEffects.playClip("ding.wav");
		switchConfirmPanel.setText(p1Name, p2Name, isP1Turn);
	}

	/**
	 * Switches to a different panel.
	 * 
	 * @param panelString the panel to switch to, see the constants named CARD
	 */
	void switchPanel(String panelString) {
		rootLayout.show(rootPanel, panelString);
	}

	void saveLeaderboard() {
		try {
			System.out.println("Save leaderboard");
			leaderboard.save();
		} catch (IOException e1) {
			// show a dialog to inform the user
			JOptionPane.showMessageDialog(null,
					"Could not save the leaderboard for some reason. The program will exit now.",
					"Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gets the globally-owned leaderboard instance.
	 * 
	 * @return the leaderboard
	 */
	public Leaderboard getLeaderboard() {
		return leaderboard;
	}

}
