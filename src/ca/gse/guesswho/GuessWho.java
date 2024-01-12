/*
GuessWho.java
Authors: Jacky Guo, Winston Zhao
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho;

import java.io.File;
import java.io.IOError;
import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.sound.MidiPlayer;
import ca.gse.guesswho.sound.SoundEffects;
import ca.gse.guesswho.views.DialogUtilities;
import ca.gse.guesswho.views.MainWindow;

/**
 * Main class for the game. Manages the main window, and switches
 * between the various panels that comprise the main layout.
 */
public class GuessWho {
	private static File getLeaderboardPath() {
		// use Java NIO paths, as it provides more intuitive ways to combine path
		// 
		Path homePath = Paths.get(System.getProperty("user.home"));
		File leaderboardFile = homePath.resolve("GSEGuessWho_Leaderboard.csv").toFile();
		System.out.println("Leaderboard: " + leaderboardFile);
		return leaderboardFile;
	}
	
	
	/**
	 * The program's main method. Loads data, sets up the theme, and starts the application.
	 * @param args command-line arguments; currently unused.
	 */
    public static void main(String[] args) {
		try {
			System.out.println("Loading character data...");
			DataCaches.loadCharacters(GuessWho.class.getResource("CharacterData.csv"));
			System.out.println("Loading questions...");
			DataCaches.loadQuestions(GuessWho.class.getResource("QuestionBank.csv"));
			System.out.println("Loading sound effects...");
			SoundEffects.loadClips();
			System.out.println("Loading music...");
			MidiPlayer.loadSequences();
		}
		catch (Exception e) {
			// errors during load are fatal, 
			DialogUtilities.showExceptionDialog(null, "An error occurred during loading", e);
      System.exit(1);
		}
		
		// System.out.println("Characters:");
		// for (GuessWhoCharacter character : DataCaches.getCharacterList()) {
		// 	System.out.println(character);
		// }
		// System.out.println();
		
		// System.out.println("Questions: ");
		// for (Map.Entry<String, AttributeQuestion> entry : DataCaches.getQuestions().entrySet()) {
		// 	System.out.printf("%s (%s)\n", entry.getKey(), entry.getValue());
		// }
		// System.out.println();

    UIManager.put("nimbusBase", new Color(0xff8888));
    UIManager.put("nimbusFocus", Color.BLUE); // Red color
    UIManager.put("background", Color.WHITE);
    UIManager.put("nimbusSelectionBackground", new Color(0xff5224));
    UIManager.put("nimbusBlueGrey", new Color(0xffb5a1));
    UIManager.put("control", Color.WHITE);
    //UIManager.put("Button[Default+Pressed].backgroundPainter", Color.BLUE);
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.out.println("Could not retrieve Nimbus theme, reverting to default theme");
		}
		
		
		MainWindow window = new MainWindow(getLeaderboardPath());
		window.setVisible(true);
    }
}