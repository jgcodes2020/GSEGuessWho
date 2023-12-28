package ca.gse.guesswho.components;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import ca.gse.guesswho.models.GuessWhoCharacter;

/**
 * A display for a character that can be selected and crossed out.
 * This combines a toggle button, a label, and a character image display.
 */
public class CharacterCard extends JToggleButton {

	private JLabel textLabel;
	private CharacterImageDisplay iconDisplay;

	/**
	 * Creates a new character card for the given character.
	 * @param character The Guess Who character to set on this CharacterCard.
	 */
	public CharacterCard(GuessWhoCharacter character) {
		setLayout(new BorderLayout());

		// setBackground(Color.WHITE);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		textLabel = new JLabel(character.getName());
		textLabel.setHorizontalAlignment(JLabel.CENTER);

		iconDisplay = new CharacterImageDisplay(character.getImage());

		add(iconDisplay, BorderLayout.CENTER);
		add(textLabel, BorderLayout.SOUTH);
	}

	/**
	 * Sets the "crossed out status" for this character card.
	 * @param value the crossed out status to set.
	 * @see CharacterImageDisplay#setCrossedOut(boolean)
	 */
	public void setCrossedOut(boolean value) {
		iconDisplay.setCrossedOut(value);
	}
}
