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

public class DialogUtilities {
    private static String stackTraceText(Throwable throwable) {
        try (StringWriter output = new StringWriter(); PrintWriter printer = new PrintWriter(output)) {
            throwable.printStackTrace(printer);
            return output.toString();
        } catch (IOException e) {
            return throwable.toString();
        }
    }

    public static void showExceptionDialog(Component parent, String message, Throwable throwable) {
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message);
        basePanel.add(messageLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(10, 40);
        textArea.setText(stackTraceText(throwable));
        basePanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JOptionPane.showMessageDialog(parent, basePanel, "Oh noes!", JOptionPane.ERROR_MESSAGE);
    }
}
