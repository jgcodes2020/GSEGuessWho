package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

import ca.gse.guesswho.sound.SoundEffects;

/**
 * Panel displaying settings. For now, this is only the music and SFX volume sliders.
 */
public class SettingsPanel extends JPanel {
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
	private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);

	private MainWindow main;
	private JSlider sfxVolume;
	private JSlider musicVolume;

	/**
	 * Creates a simple wrapping panel with a title in its border.
	 * 
	 * @param title the title to put in the border
	 * @return the titled panel.
	 */
	private static JPanel createTitledPanel(String title) {
		JPanel board = new JPanel();
		TitledBorder titleBorder = BorderFactory.createTitledBorder("SFX Volume");
		board.setBorder(titleBorder);
		return board;
	}

	/**
	 * Creates a menu button. All menu buttons have a bunch of shared properties,
	 * so I'm putting this in a method.
	 * 
	 * @param text the text to put on the button.
	 * @return the mostly set-up button.
	 */
	private static JButton createMenuButton(String text) {
		JButton result = new JButton(text);
		result.setFont(BUTTON_FONT);
		result.setMaximumSize(new Dimension(400, result.getMaximumSize().height));
		result.setAlignmentX(CENTER_ALIGNMENT);

		return result;
	}

	private JPanel buildContentPanel() {
		JPanel board = new JPanel();
		board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
		board.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

		// label for SFX volume
		JLabel sfxLabel = new JLabel("SFX volume");
		sfxLabel.setAlignmentX(CENTER_ALIGNMENT);
		sfxLabel.setHorizontalAlignment(JLabel.CENTER);

		// sound effect volume slider
		sfxVolume = new JSlider();
		sfxVolume.setMinimum(0);
		sfxVolume.setMaximum(100);
		// sliders operate on integer values, convert from the fraction used in sound
		// API
		sfxVolume.setValue((int) (SoundEffects.getVolume() * 100.0));
		sfxVolume.addChangeListener(this::onSfxVolumeChange);
		sfxVolume.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				onSfxVolumeMouseReleased(e);
			}
		});

		// music label
		JLabel musicLabel = new JLabel("Music Volume");
		musicLabel.setAlignmentX(CENTER_ALIGNMENT);
		musicLabel.setHorizontalAlignment(JLabel.CENTER);

		// music volume slider
		musicVolume = new JSlider();
		musicVolume.setMinimum(0);
		musicVolume.setMaximum(100);
		musicVolume.addChangeListener(this::onMusicVolumeChange);

		board.add(sfxLabel);
		board.add(sfxVolume);
		board.add(musicLabel);
		board.add(musicVolume);
		board.add(Box.createVerticalGlue());

		return board;
	}

	public SettingsPanel(MainWindow mainWindow) {
		main = mainWindow;
		setLayout(new BorderLayout());

		JLabel bigTitle = new JLabel("SETTINGS");
		bigTitle.setFont(TITLE_FONT);
		bigTitle.setHorizontalAlignment(JLabel.CENTER); // Center the title
		add(bigTitle, BorderLayout.NORTH);

		JPanel content = buildContentPanel();
		content.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));

		JPanel contentWrapper = new JPanel();
		contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.X_AXIS));
		contentWrapper.add(Box.createHorizontalGlue());
		contentWrapper.add(content);
		contentWrapper.add(Box.createHorizontalGlue());
		// JScrollPane wrap = new JScrollPane(content);
		add(contentWrapper, BorderLayout.CENTER);

		JButton backToMainMenuButton = createMenuButton("Back to main menu");
		backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
		add(backToMainMenuButton, BorderLayout.SOUTH);
	}

	/**
	 * Sets the sound-effect volume to
	 * 
	 * @param e
	 */
	private void onSfxVolumeChange(ChangeEvent e) {
		// slider's range is [0, 100], map to [0, 1]
		SoundEffects.setVolume(sfxVolume.getValue() / 100.0);
	}

	/**
	 * Plays the ding sound effect to let the user know how the loud the
	 * sound-effects are.
	 * 
	 * @param e release
	 */
	private void onSfxVolumeMouseReleased(MouseEvent e) {
		// sometimes this doesn't play the sound properly. The solution requires more
		// extra work than I think is worth doing.
		SoundEffects.playClip("ding.wav");
	}

	/**
	 * Sets the music volume based on the value of the slider.
	 * 
	 * @param e
	 */
	private void onMusicVolumeChange(ChangeEvent e) {
		// slider's range is [0, 100], map to [0, 1]
		main.getMidiPlayer().setVolume(musicVolume.getValue() / 100.0);
	}

	/**
	 * Basically just switch the panel back to the main menu.
	 * 
	 * @param e the event being handled.
	 */
	private void onBackToMainMenuPressed(ActionEvent e) {
		main.switchPanel(MainWindow.CARD_MENU);
	}
}
