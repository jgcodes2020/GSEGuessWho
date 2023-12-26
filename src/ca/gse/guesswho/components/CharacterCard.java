package ca.gse.guesswho.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import java.util.List;
import java.util.ArrayList;

import ca.gse.guesswho.events.CharacterSelectEvent;
import ca.gse.guesswho.models.GuessWhoCharacter;

/**
 * Represents a character card. Subclasses JToggleButton, as this lets us give
 * it button behaviour that can be themed using Swing's own theming mechanism.
 */
public class CharacterCard extends JToggleButton {
	// even though Java was developed by Americans, and spells "colour" that way
	// I refuse to spell it the wrong way.
	private static Color HIGHLIGHT_COLOUR = new Color(0x0088FF);
	private static Color SELECT_COLOUR = new Color(0x85C6FF);

	private JLabel textLabel;
	private CharacterImageDisplay iconLabel;
	private boolean clickable = true;
	private boolean selected;

	private List<Consumer<CharacterSelectEvent>> selectHandlers = new ArrayList<>();

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

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean state) {
		clickable = state;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
