/*
TutorialPanel.java
Authors: Jacky Guo, Chapman Yu, Winston Zhao
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel displaying basic instructions for the game.
 */
public class TutorialPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	private static final String[] INSTRUCTION_LINES = { // variable
			"1) Choose your character! (take picture to remember it if necessary)",
			"2) Select a question to get clues about your opponent's character. (eliminates characters that do not match the question)",
			"3) Answer your opponent's yes/no question based on your chosen character",
			"4) If you think you know your opponent's character, select the character to guess",
			"5) If you are correct, you win! otherwise, you lose.",
			"6) Guess your opponent's character before they guess yours!"
	};

	private MainWindow main;
	private JLabel instructionLabel;
	private int currentInstructionIndex = 0;

	/**
	 * Creates a menu button. All menu buttons have a bunch of shared properties,
	 * so I'm putting this in a method.
	 * 
	 * @param text the text to put on the button.
	 * @return the mostly set-up button.
	 */
	private static JButton createMenuButton(String text) {
		JButton result = new JButton(text);
		result.setFont(BUTTON_FONT);
		result.setMaximumSize(new Dimension(400, result.getMaximumSize().height));
		result.setAlignmentX(CENTER_ALIGNMENT);

		return result;
	}

	/**
	 * Sets up a win screen panel.
	 * 
	 * @param mainWindow the main window to link with this win screen panel
	 */
	public TutorialPanel(MainWindow mainWindow) {
		main = mainWindow;

		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());

		// create the "title"
		JLabel bigTitle = new JLabel("How to Play . . .");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);

		// Add instructions
		instructionLabel = new JLabel(INSTRUCTION_LINES[currentInstructionIndex]);
		instructionLabel.setFont(BUTTON_FONT);
		instructionLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(instructionLabel);

		add(Box.createVerticalStrut(20)); // 20 pixels of vertical space

		// Add next button
		JButton nextButton = createMenuButton("Next step");
		nextButton.setBackground(new Color(0x809451));
		nextButton.addActionListener(this::onNextPressed);
		add(nextButton);

		// start button
		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		backToMainMenuButton.setBackground(new Color(0xffffff));
		add(backToMainMenuButton);

		// this "vertical glue" fills up extra space at the bottom
		add(Box.createVerticalGlue());
	}

	/**
	 * Switch the label text to the next card.
	 * 
	 * @param e the event being handled.
	 */
	private void onNextPressed(ActionEvent e) {
		currentInstructionIndex++;
		if (currentInstructionIndex >= INSTRUCTION_LINES.length) {
			currentInstructionIndex = 0; // loop back to the start
		}
		instructionLabel.setText(INSTRUCTION_LINES[currentInstructionIndex]);
	}

	/**
	 * Basically just switch the panel back to the main menu.
	 * 
	 * @param e the event being handled.
	 */
	private void onBackToMainMenuPressed(ActionEvent e) {
		currentInstructionIndex = 0; // reset the tutorial
		instructionLabel.setText(INSTRUCTION_LINES[currentInstructionIndex]); // update the label
		main.switchPanel(MainWindow.CARD_MENU);
	}
}
