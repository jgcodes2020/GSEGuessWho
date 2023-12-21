package ca.gse.guesswho.views;

import java.awt.Font;

import javax.swing.*;

public class MenuPanel extends JPanel {
	public MenuPanel() {
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel bigTitle = new JLabel("GUESS WHO \u2122");
		bigTitle.setFont(new Font("Dialog", Font.BOLD, 30));
		add(bigTitle);
		
		JButton start = new JButton("START");
		add(start);
	}
}