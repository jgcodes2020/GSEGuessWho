/*
CreditPanel.java
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

/**
 * Panel showing credits for the game.
 */
public class CreditPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	private static final String[] DISPLAY_LINES = { // variable
			"Chapman Yu",
			"Jacky Guo",
			"Winston Zhao"
	};
	private static final String DISPLAY_HTML;

	// Static initializer block: construct HTML from the provided lines.
	static {
		StringBuilder text = new StringBuilder("<html><body>");
		for (String line : DISPLAY_LINES) {
			// these characters can mess up html, they need to be replaced with html
			// entities
			String escaped = line.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
			text.append(escaped);
			text.append("<br>");
		}
		text.append("</body></html>");
		DISPLAY_HTML = text.toString();
	}

	private MainWindow main;
	private JLabel bigTitle;
	private JLabel display;

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
	public CreditPanel(MainWindow mainWindow) {
		main = mainWindow;

		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());

		// create the "title"
		bigTitle = new JLabel("This game was created by");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);
		add(Box.createVerticalStrut(50));

		// create the credits lines
		display = new JLabel(DISPLAY_HTML);
		display.setFont(BUTTON_FONT);
		display.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		display.setHorizontalAlignment(JLabel.CENTER);
		add(display);

		add(Box.createVerticalStrut(50));

		// start button
		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		add(backToMainMenuButton);

		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());

	}

	/**
	 * Basically just switch the panel back to the main menu.
	 * 
	 * @param e the event being handled.
	 */
	private void onBackToMainMenuPressed(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_MENU);
	}
}
