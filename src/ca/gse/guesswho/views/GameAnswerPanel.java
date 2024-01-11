/*
GameAnswerPanel.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.players.HumanPlayer;

public class GameAnswerPanel extends JPanel {
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	private static final Font PROMPT_FONT = new Font("Dialog", Font.BOLD, 40);

    private GamePanel parent;
	
    private JLabel questionLabel;	
    private String questionStored;
	
    private JPanel responsePanel;
	private JPanel questionPanel;
	
	private JButton yesButton;
	private JButton noButton;

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
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				GamePanel parent = GameAnswerPanel.this.parent;
				Question question = parent.getState().getLastQuestion();
				setQuestion(DataCaches.getQuestionString(question));
			}
		});
    }



	/**
	 * Internal method. Creates a JPanel for the top bar (The question bar).
	 * 
	 * @return a JPanel for the top bar.
	 */
    private JPanel buildTopBar(){
        JPanel board = new JPanel();
        questionLabel = new JLabel("");
		questionLabel.setFont(PROMPT_FONT);
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
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
		
		yesButton.addActionListener(this::onResponseClicked);
		noButton.addActionListener(this::onResponseClicked);
		yesButton.setFont(BUTTON_FONT);
		noButton.setFont(BUTTON_FONT);

        board.add(yesButton);
        board.add(noButton);
        return board;
    }
	
	private void onResponseClicked(ActionEvent e) {
		Object source = e.getSource();
		boolean check;
		GameState state = parent.getState();
		HumanPlayer player = (HumanPlayer) state.getCurrentPlayer();
		if (source == yesButton) {
			player.setNextAnswer(true);
		}
		else if (source == noButton) {
			player.setNextAnswer(false);
		}
		check = parent.runOneTurn();
		if (check == false){
			parent.runAITurnsAndSwitchPanel();

		}
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
