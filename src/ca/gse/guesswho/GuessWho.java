package ca.gse.guesswho;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.*;

import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.views.GamePanel;
import ca.gse.guesswho.views.MenuPanel;
import ca.gse.guesswho.views.WinScreenPanel;

/**
 * Main class for the game. Manages the main window, and switches
 * between the various panels that comprise the main layout.
 */
public class GuessWho {
	private static JFrame frame = null;
	private static JPanel rootPanel = null;
	private static CardLayout rootLayout = null;
	
	private static MenuPanel menuPanel = null;
	private static GamePanel gamePanel = null;
	private static WinScreenPanel winPanel = null;
	
	/**
	 * Creates a JFrame for the whole GUI.
	 * @return the JFrame for the whole GUI.
	 */
	private static JFrame buildWindow() {
		frame = new JFrame("Guess Me");
		frame.setSize(1440, 1024);
		
		// CardLayout lets us switch beetween various panels
		// programatically.
		rootLayout = new CardLayout();
		
		rootPanel = new JPanel();
		rootPanel.setLayout(rootLayout);
	
		menuPanel = new MenuPanel();
		menuPanel.addStartPressedListener(GuessWho::onGameStart);
		rootPanel.add(menuPanel, "menu");
		
		frame.setContentPane(rootPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	/**
	 * Called when the game is supposed to start.
	 */
	private static void onGameStart() {
		// initialize game state
		gamePanel = new GamePanel(new HumanPlayer("GSETestUser"), new DumbAIPlayer());
		rootPanel.add(gamePanel, "game");
		
		// switch to "game" panel
		rootLayout.show(rootPanel, "game");
	}
	
	/**
	 * The program's main method. Loads data, sets up the theme, and starts the application.
	 * @param args command-line arguments; currently unused.
	 * @throws IOException if an I/O error occurs.
	 */
    public static void main(String[] args) throws IOException {
		DataCaches.loadCharacters(GuessWho.class.getResource("CharacterData.csv"));
		DataCaches.loadQuestions(GuessWho.class.getResource("QuestionBank.csv"));
		
		// System.out.println("Characters:");
		// for (GuessWhoCharacter character : DataCaches.getCharacterList()) {
		// 	System.out.println(character);
		// }
		// System.out.println();
		
		// System.out.println("Questions: ");
		// for (Map.Entry<String, AttributeQuestion> entry : DataCaches.getQuestions().entrySet()) {
		// 	System.out.printf("%s (%s)\n", entry.getKey(), entry.getValue());
		// }
		// System.out.println();
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.out.println("Could not retrieve Nimbus theme, reverting to default theme");
		}
		
		buildWindow().setVisible(true);
    }
}