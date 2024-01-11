package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static final String[] INSTRUCTION_LINES = { // variable
		"1) CHOOSE YOUR CHARACTER (take picture to remember it if necessary)",
		"2) CHOOSE AI DIFFICULTY (easy/hard)",
    "3) SELECT A QUESTION TO GET CLUES ABOUT YOUR OPPONENT'S CHARACTER. (eliminates characters that do not match the question)",
    "4) ANSWER YOUR OPONENT'S YES/NO QUESTION BASED ON YOUR CHOSEN CHARACTER",
    "5) IF YOU THINK YOU KNOW YOUR OPONENT'S CHARACTER, SELECT THE CHARACTER TO GUESS",
    "6) IF YOU ARE CORRECT, YOU WIN! OTHERWISE, YOU LOSE!",
    "7) GUESS YOUR OPONENT'S CHARACTER BEFORE THEY GUESS YOURS!"
	};
	private static final String INSTRUCTION_HTML;
  private JLabel instructionLabel;
    private int currentInstructionIndex = 0;
	
	static {
		StringBuilder text = new StringBuilder("<html><body>");
		for (String line : INSTRUCTION_LINES) {
			// these characters can mess up html, they need to be replaced with html entities
			String escaped = line.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
			text.append(escaped);
			text.append("<br>");
		}
		text.append("</body></html>");
		INSTRUCTION_HTML = text.toString();
	}
	
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
    
    // create the "title"
    bigTitle = new JLabel("How to Play . . .");
    bigTitle.setFont(TITLE_FONT);
    bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
    add(bigTitle);

    // Add instructions
    instructionLabel = new JLabel(INSTRUCTION_LINES[currentInstructionIndex]);
    instructionLabel.setFont(BUTTON_FONT);
    instructionLabel.setAlignmentX(CENTER_ALIGNMENT);
    add(instructionLabel);

    add(Box.createRigidArea(new Dimension(0, 20))); // 20 pixels of vertical space

    // Add next button
    JButton nextButton = createMenuButton("Next step");
    nextButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentInstructionIndex++;
            if (currentInstructionIndex >= INSTRUCTION_LINES.length) {
                currentInstructionIndex = 0; // loop back to the start
            }
            instructionLabel.setText(INSTRUCTION_LINES[currentInstructionIndex]);
        }
    });
    add(nextButton);

    // start button
		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		add(backToMainMenuButton);

    // this "vertical glue" fills up extra space at the bottom
    add(Box.createVerticalGlue());
		
	}
	

	/**
	 * Basically just switch the panel back to the main menu.
	 * @param e the event being handled.
	 */
	private void onBackToMainMenuPressed(ActionEvent e) {
    currentInstructionIndex = 0; // reset the tutorial
    instructionLabel.setText(INSTRUCTION_LINES[currentInstructionIndex]); // update the label
		main.switchPanel(MainWindow.CARD_MENU);
	}
}
