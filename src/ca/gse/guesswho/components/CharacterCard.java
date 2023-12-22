package ca.gse.guesswho.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterCard extends JPanel {
	// even though Java was developed by Americans, and spells "colour" that way
	// I refuse to spell it the wrong way.
	private static Color HIGHLIGHT_COLOUR = new Color(0x0088FF);
	
	private JLabel textLabel;
	private ImageDisplay iconLabel;
	
	public CharacterCard(String name, Image image) {
		setLayout(new BorderLayout());
		
		textLabel = new JLabel(name);
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		
		iconLabel = new ImageDisplay(image);
		
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
		this.setBackground(HIGHLIGHT_COLOUR);
		this.repaint();
	}
	
	private void handleMouseExited(MouseEvent e) {
		this.setBackground(null);
		this.repaint();
		
	}
}
