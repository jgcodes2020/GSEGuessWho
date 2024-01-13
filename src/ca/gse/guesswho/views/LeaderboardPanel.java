/*
LeaderboardPanel.java
Authors: Jacky Guo, Chapman Yu, Winston Zhao
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class LeaderboardPanel extends JPanel {
    private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 60);
    private static final Font BUTTON_FONT = new Font("Dialog", Font.BOLD, 20);

    private MainWindow main;

    private JTable bigTable;

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

    /**
     * Sets up the leaderboard panel.
     * 
     * @param mainWindow the main window to link with this win screen panel
     */
    public LeaderboardPanel(MainWindow mainWindow) {
        main = mainWindow;

        setLayout(new BorderLayout());

        // Add a margin of 60 pixels on every side
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JLabel bigTitle = new JLabel("LEADERBOARD");
        bigTitle.setFont(TITLE_FONT);
        bigTitle.setHorizontalAlignment(JLabel.CENTER); // Center the title
        add(bigTitle, BorderLayout.NORTH);

        // big table for all leaderboard entries.

        bigTable = new JTable(main.getLeaderboard());

        bigTable.getTableHeader().setReorderingAllowed(false);
        bigTable.getTableHeader().setResizingAllowed(false); // Make the table non-resizable
        bigTable.setRowHeight(50);

        // Set the default sizes of each column separately
        TableColumn column1 = bigTable.getColumnModel().getColumn(0);
        column1.setPreferredWidth(300);

        TableColumn column2 = bigTable.getColumnModel().getColumn(1);
        column2.setPreferredWidth(10);

        // wrapped in scroll area, in case we ever get that many leaderboard entries.
        JScrollPane scroller = new JScrollPane(bigTable);
        add(scroller, BorderLayout.CENTER);

        JButton backToMainMenuButton = createMenuButton("Back to main menu");
        backToMainMenuButton.addActionListener(this::onBackToMainMenuPressed);
        add(backToMainMenuButton, BorderLayout.SOUTH);
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
