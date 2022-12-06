package ui;

import javax.swing.*;
import java.awt.*;

public class GameView {
    private JFrame mainWindow;
    private JPanel mainContainer;
    private JLayeredPane boardLayeredPane;
    private JPanel actionDialogBoxes;
    private JPanel bottomBox;
    private JPanel splitRHS;
    private JPanel autosaveInfo;

    /**
     * Construct the default GameView
     */
    public GameView() {
        constructMainWindow();
        constructMainContainer(mainWindow);
        constructLHS(mainContainer);
        constructRHSTop();
        constructRHSBottom();
        constructRHS(mainContainer, actionDialogBoxes, bottomBox);
    }

    private void constructMainWindow() {
        mainWindow = new JFrame("Monopoly Game");
        mainWindow.setSize(1920, 1080);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void constructMainContainer(JFrame mainWindow) {
        mainContainer = new JPanel();
        GridLayout splitPane = new GridLayout();
        mainContainer.setLayout(splitPane);
        mainWindow.add(mainContainer);
    }

    private void constructLHS(JPanel mainContainer) {
        boardLayeredPane = new JLayeredPane();
        mainContainer.add(boardLayeredPane);
    }

    private void constructRHSTop() {
        actionDialogBoxes = new JPanel();
        CardLayout cardLayout = new CardLayout();
        actionDialogBoxes.setLayout(cardLayout);
    }

    private void constructRHSBottom() {
        bottomBox = new JPanel();
        JLabel bottomLabel = new JLabel("Placeholder");
        bottomBox.add(bottomLabel);
        autosaveInfo = new JPanel();
        bottomBox.add(autosaveInfo);
    }

    private void constructRHS(JPanel mainContainer, JPanel topHalf, JPanel bottomHalf) {
        splitRHS = new JPanel();
        GridLayout verticalSplit = new GridLayout(2, 1);
        splitRHS.setLayout(verticalSplit);
        splitRHS.add(topHalf);
        splitRHS.add(bottomHalf);
        mainContainer.add(splitRHS);
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public JPanel getMainContainer() {
        return mainContainer;
    }

    public JLayeredPane getBoardLayeredPane() {
        return boardLayeredPane;
    }

    public JPanel getActionDialogBoxes() {
        return actionDialogBoxes;
    }

    public JPanel getBottomBox() {
        return bottomBox;
    }

    public JPanel getSplitRHS() {
        return splitRHS;
    }

    public JPanel getAutosaveInfo() {
        return autosaveInfo;
    }
}
