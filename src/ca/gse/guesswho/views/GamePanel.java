package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;

public class GamePanel extends JPanel {
	private GameState state;
	private CharacterCard[] cards;
	
	private JPanel buildBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(4, 6));
		
		final int characterAmt = 24;
		cards = new CharacterCard[characterAmt];

		for (int i = 0; i < characterAmt; i++){
			GuessWhoCharacter character = GameState.getCharacterList().get(i);
			
			CharacterCard curCard = new CharacterCard(character.getName(),null);
			cards[i] = curCard;
			board.add(cards[i]);
		}
		return board;
	}
	
	public GamePanel(Player p1, Player p2) {
		state = new GameState(p1, p2);
		
		setLayout(new BorderLayout());
		add(buildBoard(), BorderLayout.CENTER);
		
	}
}
