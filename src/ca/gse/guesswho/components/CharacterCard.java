package ca.gse.guesswho.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterCard extends JPanel {
	private JLabel textLabel;
	private JLabel iconLabel;
	
	public CharacterCard(String name, Image image) {
		setLayout(new BorderLayout());
		
		textLabel = new JLabel(name);
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		
		if (image == null)
			iconLabel = new JLabel("<insert icon here>");
		else
			iconLabel = new JLabel(new ImageIcon(image));
		
		add(iconLabel, BorderLayout.CENTER);
		add(textLabel, BorderLayout.SOUTH);
		
		// We use an anonymous class as a convenience when creating the mouse listeners
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				CharacterCard.this.handleMouseEntered(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				CharacterCard.this.handleMouseExited(e);
			}
		});
	}
	
	private void handleMouseEntered(MouseEvent e) {
		this.setBackground(Color.YELLOW);
		this.repaint();
	}
	
	private void handleMouseExited(MouseEvent e) {
		this.setBackground(null);
		this.repaint();
		
	}
}