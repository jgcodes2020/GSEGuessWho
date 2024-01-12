/*
WinScreenPanel.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.gse.guesswho.events.GameWonEvent;
import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.history.GameHistory;
import ca.gse.guesswho.sound.SoundEffects;

public class WinScreenPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);
	
	private static String[] characterNameList;
	private static void setupCharacterNameList() {
		List<GuessWhoCharacter> characters = DataCaches.getCharacterList();
		if (characters == null)
			throw new IllegalStateException("Character list is not loaded!");
		
		characterNameList = new String[characters.size()];
		for (int i = 0; i < characterNameList.length; i++) {
			characterNameList[i] = characters.get(i).getName();
		}
	}
	
	private MainWindow main;
	private JLabel bigTitle;
	
	private GameHistory history;
	
	/**
	 * Creates a menu button. All menu buttons have a bunch of shared properties,
	 * so I'm putting this in a method.
	 * @param text the text to put on the button.
	 * @return the mostly set-up button.
	 */
	private static JButton createMenuButton(String text){
		JButton result = new JButton(text);
		result.setFont(BUTTON_FONT);
		result.setMaximumSize(new Dimension(400, result.getMaximumSize().height));
		result.setAlignmentX(CENTER_ALIGNMENT);
		
		return result;
	}
	
	/**
	 * Sets up a win screen panel.
	 * @param mainWindow the main window to link with this win screen panel
	 */
	public WinScreenPanel(MainWindow mainWindow) {
		main = mainWindow;
		
		// this needs to be setup on first run
		if (characterNameList == null)
			setupCharacterNameList();
		
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// this "vertical glue" fills up extra space at the top
		add(Box.createVerticalGlue());
		
		// create the title text (this could be replaced with actual logo art if we wanted)
		bigTitle = new JLabel("");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setAlignmentX(CENTER_ALIGNMENT); // everything is centered anyways, so yeah.
		add(bigTitle);
		
		add(Box.createVerticalStrut(50));
		
		JButton saveLogButton = createMenuButton("Save game log");
		saveLogButton.addActionListener(this::onSaveLogPressed);
		add(saveLogButton);
		
		// back to main menu button
		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		add(backToMainMenuButton);
		
		// this "vertical glue" fills up extra space at the bottom, the combined
		// effects of the top and bottom will center everything else in the middle
		add(Box.createVerticalGlue());
		
	}
	
	/**
	 * Changes the text on the GUI to either a losing message or a winning message
	 * 
	 * @param event the events that basically just shows who won (When someone wins)
	 */
	void updateView(GameWonEvent event) {
		if (event.getHistory().getP1IsAI() || event.getHistory().getP2IsAI()){
			if (event.isWinnerP1()){
				bigTitle.setText("YOU WIN! :D");
				SoundEffects.playClip("winning.wav");
			}
			else{
				bigTitle.setText("YOU LOSE! :c ");
				SoundEffects.playClip("sad-trombone.wav");
			}
		}
		else{
			bigTitle.setText(event.getWinnerName() +" WINS! :D");
			SoundEffects.playClip("winning.wav");
		}
		history = event.getHistory();
	}






	/**
	 * Basically just switch the panel back to the main menu.
	 * @param e the event being handled.
	 */
	private void onBackToMainMenuPressed(ActionEvent e) {
		// start the menu music again
		main.getMidiPlayer().playLoadedSequence("guesswho.mid", true);
		main.switchPanel(MainWindow.CARD_MENU);
	}
	
	private void onSaveLogPressed(ActionEvent e) {
		// Acquire unknown information for logs
		if (history.getP1Secret() == null) {
			// pop up a dialog to ask what the secret character is
			String secretName = (String) JOptionPane.showInputDialog(
				this.getTopLevelAncestor(), 
				String.format("%s, what was your secret character?", history.getP1Name()),
				"Missing log data",
				JOptionPane.QUESTION_MESSAGE, null,
				characterNameList,
				characterNameList[0]
			);
			history.setP1Secret(DataCaches.getCharacterByName(secretName));
		}
		if (history.getP2Secret() == null) {
			String secretName = (String) JOptionPane.showInputDialog(
				this.getTopLevelAncestor(), 
				String.format("%s, what was your secret character?", history.getP2Name()),
				"Missing log data",
				JOptionPane.QUESTION_MESSAGE, null,
				characterNameList,
				characterNameList[0]
			);
			history.setP2Secret(DataCaches.getCharacterByName(secretName));
		}
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		
		// prompt the user to pick a file to save
		int result = chooser.showSaveDialog(this.getTopLevelAncestor());
		if (result != JFileChooser.APPROVE_OPTION)
			return;
		
		// save the log
		File savePath = chooser.getSelectedFile();
		try {
			history.saveHistoryTo(savePath);
		}
		catch (IOException exc) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "An error occurred saving the file! Try again...");
		}
	}
}
