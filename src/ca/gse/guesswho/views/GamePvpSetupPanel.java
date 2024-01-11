/*
GamePvpSetupPabel.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;


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
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GuessWhoCharacter;

public class GamePvpSetupPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
    private static final String P1_BUTTON_TEXT = "Player 1";
    private static final String P2_BUTTON_TEXT = "Player 2";
	private static final String EASY_BUTTON_TEXT = "Easy";
	private static final String HARD_BUTTON_TEXT = "Hard";
	private MainWindow main;
	private JLabel turnErrorLabel;
	private ButtonGroup turnGroup;
    private JToggleButton p1Button;

    private JTextField p1NameInput;
	private JTextField p2NameInput;
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
	 * Sets up a game setup panel.
	 * @param mainWindow the main window to link with this game setup panel
	 */
	public GamePvpSetupPanel(MainWindow mainWindow) {
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

		// create step one text
		JLabel stepOne = new JLabel("1) Both player pick a character and remember it in your brain. (Take a picture if you need to!)");
		stepOne.setFont(BUTTON_FONT);
		stepOne.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(stepOne);//add instruction of step 1
        add(buildCharList()); //Add a grid of character


		add(Box.createVerticalStrut(30));//Seperations

		// create step two text 
		JLabel stepTwo = new JLabel("2) Enter name for Player 1!");
		stepTwo.setFont(BUTTON_FONT);
		stepTwo.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
        add(stepTwo);//Add instructions of step 2
        p1NameInput = new JTextField();
        p1NameInput.setMaximumSize(new Dimension(900, 100));
        add(p1NameInput);// add space to let people type their name

		// create step three text 
		JLabel stepThree = new JLabel("3) Enter name for Player 2!");
		stepThree.setFont(BUTTON_FONT);
		stepThree.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
        add(stepThree);//Add instructions of step 2
        p2NameInput = new JTextField();
        p2NameInput.setMaximumSize(new Dimension(900, 100));
        add(p2NameInput);// add space to let people type their name

		// create step Four text 
		JLabel stepFour = new JLabel("4) Pick who to go first!");
		stepFour.setFont(BUTTON_FONT);
		stepFour.setAlignmentX(CENTER_ALIGNMENT); //everything is centered anyways, so yeah.
		add(stepFour);//Adding instruction of step 4
        add(buildTurnSelectionList());//Adding buttons to selection

        turnErrorLabel = new JLabel();//Making a label for errors
		turnErrorLabel.setMaximumSize(new Dimension(900, 100));

        turnErrorLabel.setFont(BUTTON_FONT);
        turnErrorLabel.setAlignmentX(CENTER_ALIGNMENT); //everything is centered anyways, so yeah.
		turnErrorLabel.setHorizontalAlignment(JLabel.CENTER);
		turnErrorLabel.setForeground(Color.RED);
        add(turnErrorLabel);//Adding label


		// create start button
		JButton startButton = createMenuButton("Start Game!");
		startButton.addActionListener(this::startButtonPressed);
		add(startButton);//Adds start button
		
		add(Box.createVerticalStrut(5));//Seperation


		// Return button
        JButton menuButton = createMenuButton("Return to Menu");
		menuButton.addActionListener(this::onBackToMainMenuPressed);
		add(menuButton);//Add return button

		add(Box.createVerticalStrut(15));
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		
	}
	
	/**
	 * Creates the character list.
	 * 
	 * @return a JPanel for the character list.
	 */
    private JPanel buildCharList(){
        JPanel board = new JPanel();
        CharacterCard[] cards;
        board.setLayout(new GridLayout(4, 6));

        final int characterAmt = 24;
        cards = new CharacterCard[characterAmt];

        for (int i = 0; i < characterAmt; i++) {
            GuessWhoCharacter character = DataCaches.getCharacterList().get(i);

            cards[i] = new CharacterCard(character);
			cards[i].setEnabled(false);
            board.add(cards[i]);

        }
        return board;
    }

	/**
	 * Creates the buttons to select who goes first
	 * 
	 * @return a JPanel for the buttons selection (Turns).
	 */
    private JPanel buildTurnSelectionList() {
        turnGroup = new ButtonGroup();//Make a button group (Makes it so only one button can be selected)
        JPanel board = new JPanel();
        board.setLayout(new FlowLayout());
		board.setMaximumSize(new Dimension(900, 100));

        p1Button = new JToggleButton(P1_BUTTON_TEXT);
        JToggleButton p2Button = new JToggleButton(P2_BUTTON_TEXT);
		
		Font boldFont = p1Button.getFont().deriveFont(Font.BOLD);
		p1Button.setFont(boldFont);//Set fonts of button
		p2Button.setFont(boldFont);
		
        turnGroup.add(p1Button);//Add button into button group
        turnGroup.add(p2Button);
		
        board.add(p1Button);//Add button into board
        board.add(p2Button);
        return board;
    }



	/**
	 * A method which happens when the start button is pressed
	 * It might send you errors if there is nothing selected for some things
	 * else, it will start the game.
	 * 
	 * @param e the event being handled.
	 */
	private void startButtonPressed(ActionEvent e) {
		// Check if *any* button was selected
        ButtonModel turnSelection = turnGroup.getSelection();
        if (turnSelection != null ){
			// P1 goes first if the P1 button is selected
            main.createGamePvp(p1NameInput.getText(), p2NameInput.getText() , p1Button.isSelected() );
			resetPanel();

        }
		if (turnSelection == null){
			turnErrorLabel.setText("Please select who goes first.");
		}
		else{
			turnErrorLabel.setText("");
		}

	}



	/**
	 * Basically just switch the panel back to the main menu.
	 * @param e the event being handled.
	 */
    private void onBackToMainMenuPressed(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_MENU);
		resetPanel();
	}
	/**
	 * Resets the panel so everything will be resets next time the panel is called.
	 * 
	 */
	private void resetPanel(){
		turnGroup.clearSelection();
		p1NameInput.setText("");
		p2NameInput.setText("");

	}
    


}
