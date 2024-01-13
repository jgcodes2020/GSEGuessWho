/*
GameAnswerPanel.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.gse.guesswho.components.CharacterCard;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
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
    private JPanel characterPanel;

    private JButton yesButton;
    private JButton noButton;

    /**
     * Constructs a Answer Panel (Panel for responding yes or no to questions)
     */
    public GameAnswerPanel(GamePanel parent) {
        this.parent = parent;
        questionPanel = buildTopBar();
        responsePanel = buildResponseBar();
        characterPanel = buildCharList();
        setLayout(new BorderLayout());

        JPanel builderPanel = new JPanel();
        builderPanel.setLayout(new BorderLayout());
        builderPanel.add(responsePanel, BorderLayout.CENTER);
        builderPanel.add(questionPanel, BorderLayout.NORTH);
        builderPanel.add(Box.createVerticalStrut(30), BorderLayout.SOUTH);

        add(builderPanel, BorderLayout.NORTH);
        add(characterPanel, BorderLayout.CENTER);
        add(Box.createVerticalStrut(45), BorderLayout.SOUTH);

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
     * Creates the character list.
     * 
     * @return a JPanel for the character list.
     */
    private JPanel buildCharList() {
        JPanel realBoard = new JPanel();
        JPanel charBoard = new JPanel();
        JLabel instruction = new JLabel("Character List");

        instruction.setFont(BUTTON_FONT);
        instruction.setHorizontalAlignment(JLabel.CENTER);
        realBoard.setLayout(new BorderLayout());
        charBoard.setLayout(new GridLayout(4, 6));

        CharacterCard[] cards;

        final int characterAmt = 24;
        cards = new CharacterCard[characterAmt];

        for (int i = 0; i < characterAmt; i++) {
            GuessWhoCharacter character = DataCaches.getCharacterList().get(i);

            cards[i] = new CharacterCard(character);
            cards[i].setEnabled(false);
            charBoard.add(cards[i]);

        }

        realBoard.add(instruction, BorderLayout.NORTH);
        realBoard.add(charBoard, BorderLayout.CENTER);

        return realBoard;
    }

    /**
     * Internal method. Creates a JPanel for the top bar (The question bar).
     * 
     * @return a JPanel for the top bar.
     */
    private JPanel buildTopBar() {
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
    private JPanel buildResponseBar() {
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
        } else if (source == noButton) {
            player.setNextAnswer(false);
        }
        check = parent.runOneTurn();
        if (check == false) {
            parent.runAITurnsAndSwitchPanel();

        }
    }

    /**
     * Set the question on the top bar.
     * 
     * @param question The new question
     */
    public void setQuestion(String question) {
        questionStored = question;
        questionLabel.setText(questionStored);
    }
}
