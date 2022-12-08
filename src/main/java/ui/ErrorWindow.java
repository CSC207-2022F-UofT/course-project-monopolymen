package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class to make a new error window and exit the application. After constructing, will display a window with the message
 * that will exit the application.
 */
public class ErrorWindow {

    /**
     * After constructing, will display a window with the message
     * that will exit the application.
     */
    public static void showErrorWindow(String errorMessage) {
        JPanel errorWindow = new JPanel();
        errorWindow.setLayout(new GridBagLayout());
        GridBagConstraints rowSpecifier = new GridBagConstraints();
        rowSpecifier.insets = new Insets(5, 5, 5, 5);
        rowSpecifier.gridy = 0;
        errorWindow.add(new JLabel("An error occurred: " + errorMessage + ". Program will now exit."), rowSpecifier);
        JButton acknowledge = new JButton("ok");
        acknowledge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        errorWindow.add(acknowledge, rowSpecifier);
        JFrame errorFrame = new JFrame();
        JDialog errorDialog = new JDialog(errorFrame, "Monopoly Game encountered an error", true);
        errorDialog.setContentPane(errorWindow);
        errorDialog.pack();
        errorDialog.setLocationRelativeTo(null);
        errorDialog.setVisible(true);
    }
}
