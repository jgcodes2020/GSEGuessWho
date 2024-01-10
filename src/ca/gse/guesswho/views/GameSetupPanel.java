package ca.gse.guesswho.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GuessWhoCharacter;

public class GameSetupPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
    private static final String P1_BUTTON_TEXT = "Player 1 (You)";
    private static final String P2_BUTTON_TEXT = "Player 2 (Not You)";
	private static final String EASY_BUTTON_TEXT = "Easy";
	private static final String HARD_BUTTON_TEXT = "Hard";
	private MainWindow main;
	private JLabel turnErrorLabel;
	private JLabel aiErrorLabel;
    private CharacterCard[] cards;
    private ButtonGroup firstPlayerGroup;
	private ButtonGroup turnGroup;
    private JToggleButton p1Button;

    private JTextField nameInput;
	private JToggleButton easyAIButton;

	

	
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
	public GameSetupPanel(MainWindow mainWindow) {
		main = mainWindow;
		
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalStrut(15));
		
		// create the title text (this could be replaced with actual logo art if we wanted)
		JLabel bigTitle = new JLabel("Game Setup . . .");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);
		
		add(Box.createVerticalStrut(20));

		// create step one text (this could be replaced with actual logo art if we wanted)
		JLabel stepOne = new JLabel("1) Pick a character and remeber it in your brain. (Take a picture if you need to!)");
		stepOne.setFont(BUTTON_FONT);
		stepOne.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(stepOne);
        add(buildCharList());


		add(Box.createVerticalStrut(30));

		// create step two text 
		JLabel stepTwo = new JLabel("2) Enter your name!");
		stepTwo.setFont(BUTTON_FONT);
		stepTwo.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
        add(stepTwo);
        nameInput = new JTextField();
        nameInput.setMaximumSize(new Dimension(900, 100));
        add(nameInput);

		// create step Three text 
		JLabel stepThree = new JLabel("3) Pick the AI's diffcultity");
		stepThree.setFont(BUTTON_FONT);
		stepThree.setAlignmentX(CENTER_ALIGNMENT); //everything is centered anyways, so yeah.
		add(stepThree);
        add(buildAISelectionList());

		aiErrorLabel = new JLabel();
		aiErrorLabel.setMaximumSize(new Dimension(900, 100));

        aiErrorLabel.setFont(BUTTON_FONT);
        aiErrorLabel.setAlignmentX(CENTER_ALIGNMENT); //everything is centered anyways, so yeah.
		aiErrorLabel.setHorizontalAlignment(JLabel.CENTER);
		aiErrorLabel.setForeground(Color.RED);
        add(aiErrorLabel);

		// create step Four text 
		JLabel stepFour = new JLabel("4) Pick who to go first!");
		stepFour.setFont(BUTTON_FONT);
		stepFour.setAlignmentX(CENTER_ALIGNMENT); //everything is centered anyways, so yeah.
		add(stepFour);
        add(buildTurnSelectionList());

        turnErrorLabel = new JLabel();
		turnErrorLabel.setMaximumSize(new Dimension(900, 100));

        turnErrorLabel.setFont(BUTTON_FONT);
        turnErrorLabel.setAlignmentX(CENTER_ALIGNMENT); //everything is centered anyways, so yeah.
		turnErrorLabel.setHorizontalAlignment(JLabel.CENTER);
		turnErrorLabel.setForeground(Color.RED);
        add(turnErrorLabel);

       // add(Box.createVerticalStrut(50));

		// start button
		JButton startButton = createMenuButton("Start Game!");
		startButton.addActionListener(this::startButtonPressed);
		add(startButton);
		
		add(Box.createVerticalStrut(5));


		// back button
        JButton menuButton = createMenuButton("Return to Menu");
		menuButton.addActionListener(this::onBackToMainMenuPressed);
		add(menuButton);

		add(Box.createVerticalStrut(15));
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		
	}
	

    private JPanel buildCharList(){
        JPanel board = new JPanel();
        CharacterCard[] cards;
        board.setLayout(new GridLayout(4, 6));

        // board.setBackground(Color.RED);
        // board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

        final int characterAmt = 24;
        cards = new CharacterCard[characterAmt];


        for (int i = 0; i < characterAmt; i++) {
            GuessWhoCharacter character = DataCaches.getCharacterList().get(i);

            cards[i] = new CharacterCard(character);
            board.add(cards[i]);

        }
        return board;
    }

    private JPanel buildTurnSelectionList() {
        turnGroup = new ButtonGroup();
        JPanel board = new JPanel();
        board.setLayout(new FlowLayout());
		board.setMaximumSize(new Dimension(900, 100));

        // board.setBackground(Color.RED);
        // board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));
        p1Button = new JToggleButton(P1_BUTTON_TEXT);
        JToggleButton p2Button = new JToggleButton(P2_BUTTON_TEXT);
        turnGroup.add(p1Button);
        turnGroup.add(p2Button);
        board.add(p1Button);
        board.add(p2Button);
        return board;
    }

	private JPanel buildAISelectionList() {
        firstPlayerGroup = new ButtonGroup();
        JPanel board = new JPanel();
        board.setLayout(new FlowLayout());
		board.setMaximumSize(new Dimension(900, 100));

        // board.setBackground(Color.RED);
        // board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));
        easyAIButton = new JToggleButton(EASY_BUTTON_TEXT);
        JToggleButton hardAIButton = new JToggleButton(HARD_BUTTON_TEXT);
        firstPlayerGroup.add(easyAIButton);
        firstPlayerGroup.add(hardAIButton);
        board.add(easyAIButton);
        board.add(hardAIButton);
        return board;
    }

	private void startButtonPressed(ActionEvent e) {
		// Check if *any* button was selected
        ButtonModel turnSelection = turnGroup.getSelection();
		ButtonModel aiSelection = firstPlayerGroup.getSelection();
        if (turnSelection != null && aiSelection != null){
			// P1 goes first if the P1 button is selected
            main.createGame(nameInput.getText(), p1Button.isSelected() , (!easyAIButton.isSelected()));
			resetPanel();

        }
		if (turnSelection == null){
			turnErrorLabel.setText("Please select who goes first.");
		}
		else{
			turnErrorLabel.setText("");
		}
		if (aiSelection == null){
			aiErrorLabel.setText("Please select the AI's mode");
		}
		else{
			aiErrorLabel.setText("");

		}
	}
    private void onBackToMainMenuPressed(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_MENU);
	}

	private void resetPanel(){
		turnGroup.clearSelection();
		firstPlayerGroup.clearSelection();




	}
    


}
