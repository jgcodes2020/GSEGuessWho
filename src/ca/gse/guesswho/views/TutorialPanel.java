package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TutorialPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	
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
	public TutorialPanel(MainWindow mainWindow) {
		main = mainWindow;
		
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());
		
		// create the title text (this could be replaced with actual logo art if we wanted)
		bigTitle = new JLabel("How to Play . . .");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);
		add(Box.createVerticalStrut(50));

        steps = new JLabel("<html>1) Do something<br>2) do something else!</hmtl>");
		steps.setFont(BUTTON_FONT);
		steps.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
        steps.setHorizontalAlignment(JLabel.CENTER);
		add(steps);
		
		add(Box.createVerticalStrut(50));
		
		// start button
		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		add(backToMainMenuButton);
		
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());
		
	}
	

	
	private void onBackToMainMenuPressed(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_MENU);
	}
}
