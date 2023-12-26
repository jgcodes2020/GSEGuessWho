package ca.gse.guesswho.components;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import ca.gse.guesswho.models.GuessWhoCharacter;

/**
 * Represents a character card. Subclasses JToggleButton, as this lets us give
 * it button behaviour that can be themed using Swing's own theming mechanism.
 */
public class CharacterCard extends JToggleButton {

	private JLabel textLabel;
	private CharacterImageDisplay iconLabel;

	public CharacterCard(GuessWhoCharacter character) {
		setLayout(new BorderLayout());

		// setBackground(Color.WHITE);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		textLabel = new JLabel(character.getName());
		textLabel.setHorizontalAlignment(JLabel.CENTER);

		iconLabel = new CharacterImageDisplay(character.getImage());

		add(iconLabel, BorderLayout.CENTER);
		add(textLabel, BorderLayout.SOUTH);
	}

	public void setCrossedOut(boolean value) {
		iconLabel.setCrossedOut(value);
	}
}
