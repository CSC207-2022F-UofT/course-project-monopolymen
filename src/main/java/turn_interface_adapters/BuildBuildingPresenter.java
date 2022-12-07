package turn_interface_adapters;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import turn_use_cases.build_use_case.BuildBuildingOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuildBuildingPresenter implements BuildBuildingOutputBoundary {

    private final JPanel mainPanel;
    private final JPanel optionsPanel;
    private final TurnController controller;
    private final CardLayout cardLayout;

    public BuildBuildingPresenter(JPanel mainPanel, CardLayout cardLayout, TurnController controller) {
        this.controller = controller;
        this.cardLayout = cardLayout;

        this.mainPanel = new JPanel();
        this.optionsPanel = new JPanel();
        this.optionsPanel.setLayout(cardLayout);
        mainPanel.add(optionsPanel, "Options Panel");

    }

    /**
     * Presents the list properties which would build a building.
     *
     * @param properties the property which would build a building or sell a building.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showBuildOption(ArrayList<Property> properties, String flavorText){
        resetOptionsPanel();
        optionsPanel.add(new JLabel(flavorText));
        for (Property property : properties){
            JButton optionsButton = new JButton("Pick" + property.getTileName());
            optionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(((ColorPropertyTile) property).getNumHouses() < 4){
                        controller.buildHouse((ColorPropertyTile) property);
                    } else {
                        controller.buildHotel((ColorPropertyTile) property);
                    }
                }
            });
            optionsPanel.add(optionsButton);
        }

        showOptionsPanel();
        cardLayout.show(mainPanel, "Options Panel");
    }

    /**
     * Presents the property which would build a building.
     *
     * @param player player who wants to build a building.
     * @param property the property which would build a building.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showBuildBuilding(Player player, ColorPropertyTile property, String flavorText){
        resetOptionsPanel();
        JButton endButton = new JButton("End Build");
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
     * Presents the property on which the building would be sold.
     *
     * @param player player who wants to sell a building.
     * @param property the property on which the building would be sold.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showSellBuilding(Player player, ColorPropertyTile property, String flavorText){
        resetOptionsPanel();
        JButton endButton = new JButton("End Sell");
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
     * Presents the list properties which would sell a building.
     *
     * @param properties the property which would build a building or sell a building.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showSellOption(ArrayList<Property> properties, String flavorText){
        resetOptionsPanel();
        optionsPanel.add(new JLabel(flavorText));
        for (Property property : properties){
            JButton optionsButton = new JButton("Pick" + property.getTileName());
            optionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(((ColorPropertyTile) property).getNumHotels() > 0){
                        controller.sellHotel((ColorPropertyTile) property);
                    } else {
                        controller.sellHouse((ColorPropertyTile) property);
                    }
                }
            });
            optionsPanel.add(optionsButton);
        }

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
