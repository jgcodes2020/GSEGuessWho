package ca.gse.guesswho.views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameAnswerPanel extends JPanel {
    private GamePanel parent;
    private JLabel questionLabel;	
    private JPanel responsePanel;
	private JPanel questionPanel;
    private String questionStored;

    /**
	 * Constructs a Answer Panel (Panel for responding yes or no to questions)
	 */
    public GameAnswerPanel(GamePanel parent) {
        this.parent = parent;
        questionPanel = buildTopBar();
		responsePanel = buildResponseBar();
		setLayout(new BorderLayout());
		add(responsePanel, BorderLayout.CENTER);
		add(questionPanel, BorderLayout.NORTH);
    }



	/**
	 * Internal method. Creates a JPanel for the top bar (The question bar).
	 * 
	 * @return a JPanel for the top bar.
	 */
    private JPanel buildTopBar(){
        JPanel board = new JPanel();
        questionLabel = new JLabel("");
        board.add(questionLabel);
        return board;
    }
	/**
	 * Internal method. Creates a JPanel for the response bar .
	 * 
	 * @return a JPanel for the response bar.
	 */
    private JPanel buildResponseBar(){
        JPanel board = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        board.add(yesButton);
        board.add(noButton);
        return board;
    }
	/**
     * Set the question on the top bar
	 * 
	 * @param question The new question
	 */
    public void setQuestion(String question){
        questionStored = question;
        questionLabel.setText(questionStored);
    }
}
