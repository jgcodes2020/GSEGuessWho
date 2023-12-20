package ca.gse.guesswho.views;

import java.awt.Image;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterCard extends JPanel {
	private JLabel textLabel;
	private JLabel iconLabel;
	
	public CharacterCard(String name, Image image) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		textLabel = new JLabel(name);
		if (image == null)
			iconLabel = new JLabel("<insert icon here>");
		else
			iconLabel = new JLabel(new ImageIcon(image));
		
		add(iconLabel);
		add(textLabel);
	}
}
