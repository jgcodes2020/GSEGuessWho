package ca.gse.guesswho.views;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.BitSet;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.QuestionBankEntry;
import ca.gse.guesswho.models.players.HumanPlayer;
import ca.gse.guesswho.models.questions.CharacterQuestion;

public class GameQuestionPanel extends JPanel {
    private GamePanel parent;
    private CharacterCard[] cards;
    private ButtonGroup cardGroup;
    private JList<String> questionList;
    private JLabel errorMessage;

    public GameQuestionPanel(GamePanel parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
		add(topBarPanel(),BorderLayout.NORTH);
        add(buildBoard(), BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    /**
     * Internal method. Creates a JPanel for the game board.
     * 
     * @return a JPanel for the game board.
     */
    private JPanel buildBoard() {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(4, 6));
        // board.setBackground(Color.RED);
        // board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

        final int characterAmt = 24;
        cards = new CharacterCard[characterAmt];

        cardGroup = new ButtonGroup();

        for (int i = 0; i < characterAmt; i++) {
            GuessWhoCharacter character = DataCaches.getCharacterList().get(i);

            cards[i] = new CharacterCard(character);
            cards[i].addActionListener(this::characterCardPressed);
            cardGroup.add(cards[i]);
            board.add(cards[i]);
        }
        return board;
    }
	/**
	 * Internal method. Creates a JPanel for the bottom bar.
	 * 
	 * @return a JPanel for the bottom bar.
	 */
	private JPanel buildBottomBar() {
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.X_AXIS));
		JButton confirmButton = new JButton("Confirm");
		JButton forfeitButton = new JButton("Forfeit");
		errorMessage = new JLabel("");

		// board.setBackground(Color.RED);
		// board.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		ArrayList<String> questions = new ArrayList<String>();
		for (QuestionBankEntry entry : DataCaches.getQuestionBank()) {
			questions.add(entry.getText());
		}

		questionList = new JList<String>(questions.toArray(new String[0]));
		questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		questionList.addListSelectionListener(this::questionListValueChanged);

		JScrollPane questionScroll = new JScrollPane(questionList);

		questionScroll.setViewportView(questionList);
		questionScroll.setPreferredSize(new Dimension(540, 210));
		confirmButton.setPreferredSize(new Dimension(210, 210));
		forfeitButton.setPreferredSize(new Dimension(210, 210));
		forfeitButton.addActionListener(this::forfeitButtonPressed);
		confirmButton.addActionListener(this::submitButtonPressed);

		board.add(Box.createHorizontalGlue());
		board.add(questionScroll);
		board.add(confirmButton);
		board.add(errorMessage);
		board.add(forfeitButton);

		board.add(Box.createHorizontalGlue());
		return board;
	}
    /**
	 * Internal method. Converted/passed as an {@link ActionListener} for
	 * the submit button on the bottom bar.
	 * 
	 * @param e the event being handled.
	 */
	private void submitButtonPressed(ActionEvent e) {
		Question nextQuestion;
		
		CharacterCard.Model selectedCard = (CharacterCard.Model) cardGroup.getSelection();
		if (selectedCard != null) {
			nextQuestion = new CharacterQuestion(selectedCard.getCharacter());
		} 
		else {
			int selectedIndex = questionList.getSelectedIndex();
			if (selectedIndex == -1) {
				errorMessage.setText("You have to select a question");
				return;
			}

			// The order of questions in the question list is exactly the same as the
			// question bank.
			// We can retrieve the corresponding question by index.
			nextQuestion = DataCaches.getQuestionBank().get(selectedIndex).getQuestionObject();
		}
		// ask next question (it should be human)
		((HumanPlayer) parent.getState().getCurrentPlayer()).setNextQuestion(nextQuestion);
		parent.runPlayerTurn();
		parent.runAITurnsAndSwitchPanel();
		// show AI response
		// TODO: figure out how to show AI response HELLO WINSTON PLEASE HELP :PRAY:

		updateUIState();
		validate();
		repaint();
	}
    /**
     * Internal method. Converted/passed as an {@link ActionListener} for
     * any character cards.
     * 
     * @param e the event being handled.
     */
    private void characterCardPressed(ActionEvent e) {
        // if we select a character, then deselect any question
        // we might have selected
        questionList.clearSelection();
    }

    /**
     * Internal method. Converted/passed as an {@link ActionListener} for
     * the forfeit button on the bottom bar.
     * 
     * @param e the event being handled.
     */
	private void forfeitButtonPressed(ActionEvent e) {
		parent.forfeit();		
	}
    /**
     * Internal method. Converted/passed as a {@link ListSelectionListener} for
     * the question list in the bottom bar.
     * 
     * @param e
     */
    private void questionListValueChanged(ListSelectionEvent e) {
        // if we select a question, then deselect any character
        // we might have selected
        if (!questionList.getSelectionModel().isSelectionEmpty()) {
            cardGroup.clearSelection();
        }
    }

    /**
	 * Internal method. Updates the game board according to the
	 * current player's remaining indices.
	 */
	private void updateUIState() {
		BitSet playerRemainingIndexes = parent.getState().getCurrentPlayer().getRemainingIndexes();
		for (int i = 0; i < cards.length; i++) {
			cards[i].setCrossedOut(!playerRemainingIndexes.get(i));
		}
	}

	private JPanel topBarPanel (){
		final Font MSG_FONT = new Font("Dialog", Font.BOLD, 40);
		JPanel board = new JPanel();
		JLabel message = new JLabel("Please select a question or a character.");
		message.setFont(MSG_FONT);
        board.add(message);
        return board;
	}
}
