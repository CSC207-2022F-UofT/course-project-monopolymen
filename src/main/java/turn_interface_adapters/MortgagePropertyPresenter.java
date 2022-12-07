package turn_interface_adapters;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.Property;
import turn_use_cases.mortgage_use_case.MortgagePropertyOutputBoundary;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MortgagePropertyPresenter implements MortgagePropertyOutputBoundary {

    private final JPanel actionDialogBoxes;
    private final JPanel optionsPanel;
    private final TurnController controller;
    private final CardLayout cardLayout;




    public MortgagePropertyPresenter(JPanel actionDialogBoxes, CardLayout cardLayout, TurnController controller) {
        this.controller = controller;
        this.cardLayout = cardLayout;
        this.actionDialogBoxes = actionDialogBoxes;

        this.optionsPanel = new JPanel();
        actionDialogBoxes.add(optionsPanel, "Options Panel");

    }


    /**
     * Presents the properties which can be mortgaged.
     *
     * @param properties a list of properties which can be mortgaged.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showMortgagePropertyList(ArrayList<Property> properties, String flavorText) {
        resetOptionsPanel();
        optionsPanel.add(new JLabel(flavorText));
        for (Property property : properties){
            JButton optionsButton = new JButton("Pick" + property.getTileName());
            optionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.mortgageProperty(property);
                }
            });
            optionsPanel.add(optionsButton);
        }

        showOptionsPanel();
    }


    /**
     * Presents the properties which can be unmortgaged.
     *
     * @param properties a list of properties which can be unmortgaged.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showUnmortgagePropertyList(ArrayList<Property> properties, String flavorText) {
        resetOptionsPanel();
        optionsPanel.add(new JLabel(flavorText));
        for (Property property : properties){
            JButton optionsButton = new JButton("Pick" + property.getTileName());
            optionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.unmortgageProperty(property);
                }
            });
            optionsPanel.add(optionsButton);
        }

        showOptionsPanel();
    }


    /**
     * Presents the property which is mortgaged by player.
     *
     * @param player the player who wants to mortgage property.
     * @param property The property which is mortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showMortgageProperty(Player player, Property property, String flavorText) {
        resetOptionsPanel();
        optionsPanel.add(new JLabel(flavorText));
        JButton endButton = new JButton("End Mortgage");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.endUseCase();
            }
        });
        optionsPanel.add(endButton);
        showOptionsPanel();
    }


    /**
     * Presents the property which is unmortgaged by player.
     *
     * @param player the player who wants to unmortgage property.
     * @param property The property which is unmortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showUnmortgageProperty(Player player, Property property, String flavorText) {
        resetOptionsPanel();
        optionsPanel.add(new JLabel(flavorText));
        JButton endButton = new JButton("End Unmortgage");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.endUseCase();
            }
        });
        optionsPanel.add(endButton);
        showOptionsPanel();
    }

    private void resetOptionsPanel() {
        optionsPanel.removeAll();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
    }

    private void showOptionsPanel() {
        optionsPanel.revalidate();
        optionsPanel.repaint();
        CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
        cl.show(actionDialogBoxes, "Option Panel");
    }

}
