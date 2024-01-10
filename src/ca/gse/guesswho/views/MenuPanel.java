package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import ca.gse.guesswho.GuessWho;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.players.DumbAIPlayer;
import ca.gse.guesswho.models.players.HumanPlayer;

/**
 * Panel that displays and manages the main menu.
 */
public class MenuPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	
	// Runnable represents a void-type method with no parameters.
	// There is no additional information that needs to be carried.
	private List<Runnable> startPressedHandlers = new ArrayList<>();
	
	private MainWindow main;
	
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
	 * Constructs a MenuPanel.
	 */
	public MenuPanel(MainWindow window) {
		this.main = window;
		
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());
		
		// create the title text (this could be replaced with actual logo art if we wanted)
		JLabel bigTitle = new JLabel("GUESS WHO\u2122");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);
		
		this.getFontMetrics(TITLE_FONT).getStringBounds("foo", null);
		
		add(Box.createVerticalStrut(50));
		
		// creating and adding start button
		JButton startButton = createMenuButton("START");
		startButton.addActionListener(this::onStartPressed);
		add(startButton);
		
		// creatings and adding how to play button
		JButton howToPlayButton = createMenuButton("How to play?");
		howToPlayButton.addActionListener(this::howToPlayButtonPressed);
		add(howToPlayButton);

		JButton creditButton = createMenuButton("Credits");
		creditButton.addActionListener(this::creditButtonPressed);
		add(creditButton);
		
		// creating and adding how to exit button
		JButton exitButton = createMenuButton("Exit");
		exitButton.addActionListener(this::exitButtonPressed);
		add(exitButton);

		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());
	}
	
	/**
	 * Switches to GameSetupPanel after the start button is pressed.
	 * @param e the event parameters.
	 */
	private void onStartPressed(ActionEvent e) {
		main.switchPanel("setup");
	}
	/**
	 * Switches the panel to TutorialPanel after clicking how to play button
	 * @param e the event being handled.
	 */
	private void howToPlayButtonPressed(ActionEvent e) {
		main.switchPanel("tutorial");
	}

	private void creditButtonPressed(ActionEvent e){
		main.switchPanel("credit");
	}

	/**
	 * Stops the program after exit button is clicked
	 * @param e the event being handled.
	 */
	private void exitButtonPressed(ActionEvent e) {
		Frame [] allFrames = Frame.getFrames();
		for (int i = 0; i < allFrames.length; i++) {
			allFrames[i].dispose();
		}
	}

}