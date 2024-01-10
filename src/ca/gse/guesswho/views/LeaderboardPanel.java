package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameResult;
import ca.gse.guesswho.models.Leaderboard;
import ca.gse.guesswho.models.QuestionBankEntry;


public class LeaderboardPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);

		INSTRUCTION_HTML = text.toString();
	}
	private Leaderboard lB = new Leaderboard();
	private MainWindow main;
	private JLabel bigTitle;
	private JLabel steps;
	/**
	 * Creates a menu button. All menu buttons have a bunch of shared properties,
	 * so I'm putting this in a method.
	 * @param text the text to put on the button.
	 * @return the mostly set-up button.
	 */
	private static JButton createMenuButton(String text){
		JButton result = new JButton(text);
		result.setFont(BUTTON_FONT);
		result.setMaximumSize(new Dimension(400, result.getMaximumSize().height));
		result.setAlignmentX(CENTER_ALIGNMENT);
		
		return result;
	}
	
	/**
	 * Sets up a win screen panel.
	 * @param mainWindow the main window to link with this win screen panel
	 */
	public LeaderboardPanel(MainWindow mainWindow) {
		main = mainWindow;

		ArrayList<GameResult> list = lB.getList();
		
		JList guiList = new JList<String>();

		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());
		
		for (int i = 0;i<list.size();i++){
			guiList.add((list.get(i)).toString());
		}

		// start button
		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		add(backToMainMenuButton);
		
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());
		
	}



	private JPanel buildLeaderboardScroll(){
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.X_AXIS));

		ArrayList<GameResult> list = new ArrayList<String>();
		for (QuestionBankEntry entry : DataCaches.getQuestionBank()) {
			questions.add(entry.getText());
		}

		questionList = new JList<String>(questions.toArray(new String[0]));
		questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		questionList.addListSelectionListener(this::questionListValueChanged);

		JScrollPane questionScroll = new JScrollPane(questionList);

		questionScroll.setViewportView(questionList);
		questionScroll.setPreferredSize(new Dimension(540, 210));


		board.add(Box.createHorizontalGlue());
	}
	

	/**
	 * Basically just switch the panel back to the main menu.
	 * @param e the event being handled.
	 */
	private void onBackToMainMenuPressed(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_MENU);
	}
}
