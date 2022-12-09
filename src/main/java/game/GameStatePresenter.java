package game;

import game_entities.Player;
import turn_interface_adapters.TurnController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameStatePresenter implements GameStateOutputBoundary {
    private final JFrame mainWindow;
    private final JPanel actionDialogBoxes;
    private final JPanel turnActionsPanel;
    private final JPanel turnActionButtonsPanel;
    private final JPanel autosaveInfoPanel;
    private final String CARD_NAME;
    private final TurnController controller;

    public GameStatePresenter(JFrame mainWindow, JPanel actionDialogBoxes, TurnController controller, JPanel autosaveInfoPanel) {
        this.mainWindow = mainWindow;
        this.actionDialogBoxes = actionDialogBoxes;
        this.controller = controller;
        this.autosaveInfoPanel = autosaveInfoPanel;
        this.CARD_NAME = "turnActionsPanel";

        turnActionsPanel = new JPanel();
        turnActionsPanel.setLayout(new BorderLayout());
        turnActionButtonsPanel = new JPanel();
        turnActionButtonsPanel.setLayout(new GridLayout());
        turnActionsPanel.add(turnActionButtonsPanel, BorderLayout.SOUTH);
        actionDialogBoxes.add(turnActionsPanel, CARD_NAME);

    }

    /**
     * Show that it is the next player's turn.
     *
     * @param newPlayer  The player whose turn it now is.
     * @param turnNumber The current Turn Number. Displayed in title
     */
    @Override
    public void showNextTurn(Player newPlayer, int turnNumber) {
        resetTurnActionsPanel();
        turnActionsPanel.add(new JLabel("Player " + newPlayer.getName() + ", it's your turn!"), BorderLayout.CENTER);
        JButton acknowledgeButton = new JButton("OK");
        acknowledgeButton.addActionListener(e -> controller.endUseCase());
        turnActionButtonsPanel.add(acknowledgeButton);

        mainWindow.setTitle("Monopoly Game - Turn " + turnNumber + " - Player " + newPlayer.getName());
        showTurnActionsPanel();
    }

    /**
     * Show the current player the valid Turn Actions they can take. These TurnActions link to TurnController methods.
     *
     * @param currentPlayer The player whose turn it currently is.
     * @param validActions  A List describing what actions the player is able to take during their turn.
     */
    @Override
    public void showTurnActions(Player currentPlayer, List<TurnActions> validActions) {
        resetTurnActionsPanel();
        turnActionsPanel.add(new JLabel("Player " + currentPlayer.getName() + ", pick an action to take:"), BorderLayout.CENTER);

        for (TurnActions action : validActions) {
            switch (action) {
                case ROLL_TO_MOVE:
                    JButton rollDice = new JButton("Roll Dice");
                    rollDice.addActionListener(e -> controller.rollDice());
                    turnActionButtonsPanel.add(rollDice);
                    break;
                case LEAVE_JAIL:
                    JButton leaveJail = new JButton("Try to leave jail");
                    leaveJail.addActionListener(e -> controller.attemptLeaveJail());
                    turnActionButtonsPanel.add(leaveJail);
                    break;
                case MORTGAGE:
                    JButton mortgage = new JButton("Mortgage properties");
                    mortgage.addActionListener(e -> controller.showMortgageableProperties());
                    turnActionButtonsPanel.add(mortgage);
                    break;
                case UNMORTGAGE:
                    JButton unmortgage = new JButton("Unmortgage properties");
                    unmortgage.addActionListener(e -> controller.showUnmortgageableProperties());
                    turnActionButtonsPanel.add(unmortgage);
                    break;
                case BUILD_BUILDING:
                    JButton buildBuilding = new JButton("Build houses/hotels");
                    buildBuilding.addActionListener(e -> controller.showBuildableProperties());
                    turnActionButtonsPanel.add(buildBuilding);
                    break;
                case SELL_BUILDING:
                    JButton sellBuilding = new JButton("Sell houses/hotels");
                    sellBuilding.addActionListener(e -> controller.showBuiltOnProperties());
                    turnActionButtonsPanel.add(sellBuilding);
                    break;
                case TRADE:
                    JButton trade = new JButton("Trade");
                    trade.addActionListener(e -> controller.showTradablePlayers());
                    turnActionButtonsPanel.add(trade);
                    break;
                case END_TURN:
                    JButton endTurn = new JButton("End your turn");
                    endTurn.addActionListener(e -> controller.endTurn());
                    turnActionButtonsPanel.add(endTurn);
                    break;
            }
        }
        showTurnActionsPanel();
    }

    /**
     * Show whether the autosave was successful.
     *
     * @param successful True if the autosave was successful. False otherwise.
     */
    @Override
    public void showAutosaveStatus(boolean successful) {
        resetAutosaveInfoPanel();
        if (successful) {
            autosaveInfoPanel.add(new JLabel("Autosaved Game"));
        } else {
            autosaveInfoPanel.add(new JLabel("Autosave Failed :("));
        }
        showAutosaveInfoPanel();
    }

    private void resetAutosaveInfoPanel() {
        autosaveInfoPanel.removeAll();
    }

    /**
     * Shows the autosave info panel
     */
    private void showAutosaveInfoPanel() {
        autosaveInfoPanel.validate();
        autosaveInfoPanel.repaint();
        autosaveInfoPanel.setVisible(true);
        Timer timer = new Timer(4000, e -> autosaveInfoPanel.setVisible(false));
        timer.start();
    }

    private void resetTurnActionsPanel() {
        turnActionsPanel.removeAll();
        turnActionButtonsPanel.removeAll();
        turnActionsPanel.add(turnActionButtonsPanel, BorderLayout.SOUTH);
    }

    private void showTurnActionsPanel() {
        turnActionsPanel.revalidate();
        turnActionsPanel.repaint();
        turnActionButtonsPanel.revalidate();
        turnActionButtonsPanel.repaint();
        CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
        cl.show(actionDialogBoxes, CARD_NAME);
    }
}
