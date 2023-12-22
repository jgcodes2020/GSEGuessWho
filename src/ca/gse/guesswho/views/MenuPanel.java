package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class MenuPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	
	private List<Runnable> startPressedHandlers = new ArrayList<>();
	
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
	
	public MenuPanel() {
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
		
		// start button
		JButton startButton = createMenuButton("START");
		startButton.addActionListener(this::onStartPressed);
		add(startButton);
		
		
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());
	}
	
	/**
	 * Called whenever the start button is pressed.
	 * @param e the event parameters.
	 */
	private void onStartPressed(ActionEvent e) {
		for (Runnable handler : startPressedHandlers) {
			handler.run();
		}
	}
	
	/**
	 * Adds a callback that will be run when the start button is pressed.
	 * @param r the callback to run
	 */
	public void addStartPressedListener(Runnable r) {
		startPressedHandlers.add(r);
	}
	
	/**
	 * Removes a callback added with {@link MenuPanel#addStartPressedListener(Runnable)}.
	 * @param r the callback to remove
	 */
	public void removeStartPressedListener(Runnable r) {
		startPressedHandlers.remove(r);
	}
}