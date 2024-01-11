/*
SwitchConfirmPanel.java
Authors: Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.gse.guesswho.models.Player;


public class SwitchConfirmPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 30);
	private MainWindow main;
	private JLabel bigTitle;

	/**
	 * Creates a continue button. All menu buttons have a bunch of shared properties,
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
	public SwitchConfirmPanel (MainWindow mainWindow) {
		main = mainWindow;
		
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());
		
		// create the "title"
		bigTitle = new JLabel("Please give ME the screen.");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);
		add(Box.createVerticalStrut(50));

		// create the "steps"
        JLabel steps = new JLabel("Please pressed the button when completed");
		steps.setFont(BUTTON_FONT);
		steps.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
        steps.setHorizontalAlignment(JLabel.CENTER);
		add(steps);
		
		add(Box.createVerticalStrut(50));
		
		// start button
		JButton backToMainMenuButton = createMenuButton("Switch Complete");
		backToMainMenuButton.addActionListener(this::continueGame);
		add(backToMainMenuButton);
		
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());
		
	}
	

	/**
	 * Basically just switch the panel back to the main menu.
	 * @param e the event being handled.
	 */
	private void continueGame(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_GAME);
	}

	public void setText(String player1, String player2, boolean isPlayer1Turn){
		if (isPlayer1Turn == true){
			bigTitle.setText("Please give the screen to "+player1+".");
		}
		else{
			bigTitle.setText("Please give the screen to "+player2+".");
		}

	}

}
