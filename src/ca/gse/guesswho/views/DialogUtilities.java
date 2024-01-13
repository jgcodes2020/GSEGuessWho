/*
 * DialogUtilities.java
 * Author: Jacky Guo
 * Date: Jan. 12, 2024
 * Java version: 8
 */
package ca.gse.guesswho.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Utility functions for showing dialogs; extending those provided by {@link JOptionPane}
 */
public class DialogUtilities {
	/**
	 * Gets the stack trace for a given exception or error.
	 * @param throwable the exception or error
	 * @return the stack trace for that exception
	 */
	private static String stackTraceText(Throwable throwable) {
		try (StringWriter output = new StringWriter(); PrintWriter printer = new PrintWriter(output)) {
			throwable.printStackTrace(printer);
			return output.toString();
		} catch (IOException e) {
			return throwable.toString();
		}
	}

	/**
	 * Shows a dialog for an exception or error.
	 * @param parent the component to parent the dialog to
	 * @param message the message to display above the exception stack trace
	 * @param throwable the exception whose stack trace should be displayed
	 */
	public static void showExceptionDialog(Component parent, String message, Throwable throwable) {
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		// basic message label
		JLabel messageLabel = new JLabel(message);
		basePanel.add(messageLabel, BorderLayout.NORTH);
		// text area to display stack trace
		JTextArea textArea = new JTextArea(10, 40);
		textArea.setText(stackTraceText(throwable));
		basePanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
		// show it in a JOptionPane
		JOptionPane.showMessageDialog(parent, basePanel, "Oh noes!", JOptionPane.ERROR_MESSAGE);
	}
}
