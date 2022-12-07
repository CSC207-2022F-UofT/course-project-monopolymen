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

    private final JPanel mainPanel;
    private final JPanel optionsPanel;
    private final TurnController controller;
    private final CardLayout cardLayout;




    public MortgagePropertyPresenter(JPanel mainPanel, CardLayout cardLayout, TurnController controller) {
        this.controller = controller;
        this.cardLayout = cardLayout;

        this.mainPanel = new JPanel();
        this.optionsPanel = new JPanel();
        this.optionsPanel.setLayout(cardLayout);
        mainPanel.add(optionsPanel, "Options Panel");

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
        cardLayout.show(mainPanel, "Options Panel");
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
        cardLayout.show(mainPanel, "Options Panel");
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
        cardLayout.show(mainPanel, "Options Panel");
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
        cardLayout.show(mainPanel, "Options Panel");
    }

    private void resetOptionsPanel() {
        optionsPanel.removeAll();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
    }

    private void showOptionsPanel() {
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

}
