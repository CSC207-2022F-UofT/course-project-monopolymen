package turn_use_cases.try_to_get_out_of_jail_use_case;

import game_entities.Player;
import turn_interface_adapters.TurnController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TryToGetOutOfJailPresenter implements TryToGetOutOfJailOutputBoundary {
    private JPanel actionDialogBox;
    private JPanel playerOptionsWindow;
    private TurnController turnController;

    public TryToGetOutOfJailPresenter(JPanel actionDialogBox, TurnController turnController) {
        this.actionDialogBox = actionDialogBox;
        this.playerOptionsWindow = new JPanel();
        actionDialogBox.add(playerOptionsWindow, "Player Jail Options");

    }

    @Override
    public void showOptions(Player player, ArrayList<String> options) {
        playerOptionsWindow.removeAll();
        playerOptionsWindow.setLayout(new GridLayout(options.size(), 1));
        for (String option : options) {
            JButton optionButton = new JButton(option);
            optionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Turn Controller will handle the option and call TryToGetOutOfJailUseCase
                    turnController.leaveJailWithChoice(option);
                }
            });
            playerOptionsWindow.add(optionButton);
        }
        actionDialogBox.revalidate();
        actionDialogBox.repaint();
        CardLayout cl = (CardLayout) actionDialogBox.getLayout();
        cl.show(actionDialogBox, "Player Jail Options");
    }
}
