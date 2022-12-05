package turn_use_cases.end_turn_use_case;
import java.awt.*;
import java.awt.event.*;
import turn_interface_adapters.TurnController;
import game_entities.Player;

import javax.swing.*;

public class EndTurnPresenter implements EndTurnOutputBoundary {
    private JPanel actionDialogBox;
    private TurnController turnController;
    private JPanel endTurnWindow;

    public EndTurnPresenter(JPanel actionDialogBox, TurnController turnController) {
        this.actionDialogBox = actionDialogBox;
        this.turnController = turnController;
        this.endTurnWindow = new JPanel();
        actionDialogBox.add(endTurnWindow, "End Turn");
    }

    @Override
    public void showResultOfAction(Player player, String flavorText) {
        endTurnWindow.removeAll();
        JLabel flavorTextLabel = new JLabel(player.getName() + flavorText);
        endTurnWindow.add(flavorTextLabel);
        JButton returnToGameButton = new JButton("Return to Game");
        returnToGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnController.endTurn();
            }
        });
        endTurnWindow.add(returnToGameButton);
        actionDialogBox.revalidate();
        actionDialogBox.repaint();
        CardLayout cl = (CardLayout) actionDialogBox.getLayout();
        cl.show(actionDialogBox, "End Turn");
    }
}
