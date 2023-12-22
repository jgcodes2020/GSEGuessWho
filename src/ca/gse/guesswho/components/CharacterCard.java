package ca.gse.guesswho.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;

import ca.gse.guesswho.events.CharacterSelectEvent;
import ca.gse.guesswho.models.GuessWhoCharacter;

public class CharacterCard extends JPanel {
	// even though Java was developed by Americans, and spells "colour" that way
	// I refuse to spell it the wrong way.
	private static Color HIGHLIGHT_COLOUR = new Color(0x0088FF);
	private static Color SELECT_COLOUR = 	new Color(0x85C6FF);
	
	private JLabel textLabel;
	private CharacterImageDisplay iconLabel;
	private boolean clickable;
	private boolean selected;
	
	private List<Consumer<CharacterSelectEvent>> selectHandlers = new ArrayList<>();
	
	public CharacterCard(GuessWhoCharacter character) {
		setLayout(new BorderLayout());

    setBackground(Color.WHITE);

    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		textLabel = new JLabel(character.getName());
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		
		iconLabel = new CharacterImageDisplay(character.getImage());
		
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
			
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Consumer<CharacterSelectEvent> handler : CharacterCard.this.selectHandlers) {
					handler.accept(new CharacterSelectEvent(CharacterCard.this));
				}
			}
		});
	}
	
	public void setCrossedOut(boolean value) {
		iconLabel.setCrossedOut(value);
	}
	
	private void handleMouseEntered(MouseEvent e) {
		if (this.clickable)
			this.setBackground(HIGHLIGHT_COLOUR);
		this.repaint();
	}
	
	private void handleMouseExited(MouseEvent e) {
		this.setBackground(Color.WHITE);
		this.repaint();
	}
	
	public boolean isClickable(){
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
