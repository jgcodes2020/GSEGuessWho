package ca.gse.guesswho.views;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;

/**
 * Frame for the main window, manages transitions between panels.
 */
public class MainWindow extends JFrame {
	static final String CARD_MENU = "menu";
	static final String CARD_GAME = "game";
	static final String CARD_WIN_SCREEN = "winScreen";
	static final String CARD_ANSWER = "answer";
	static final String CARD_TUTORIAL = "tutorial";
	// UI ELEMENTS HERE
	// ----------------
	// thesse can be modified by the other view classes.
	
	JPanel rootPanel = null;
	CardLayout rootLayout = null;
	
	TutorialPanel tutorialPanel = null;
	MenuPanel menuPanel = null;
	GamePanel gamePanel = null;
	WinScreenPanel winPanel = null;
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

		setContentPane(rootPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void createGame() {
		// initialize game panel
		// TODO: improve name selection
		gamePanel = new GamePanel(this, new HumanPlayer("GSETestUser"), new DumbAIPlayer("John", DataCaches.randomCharacter()));
		// switch to game panel
		rootPanel.add(gamePanel, MainWindow.CARD_GAME);
		rootLayout.show(rootPanel, MainWindow.CARD_GAME);
	}


	
	void showWinScreen(boolean isWinnerP1) {
		GameWonEvent event = new GameWonEvent(this, isWinnerP1);
		winPanel.updateView(event);
		switchPanel(CARD_WIN_SCREEN);
	}

	public void switchPanel(String panelString){
		rootLayout.show(rootPanel, panelString);
	}

}
