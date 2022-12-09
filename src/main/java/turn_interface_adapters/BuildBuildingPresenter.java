package turn_interface_adapters;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import turn_use_cases.build_use_case.BuildBuildingOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BuildBuildingPresenter implements BuildBuildingOutputBoundary {

    private final JPanel actionDialogBoxes;
    private final JPanel choicesPanel;
    private final TurnController controller;

    public BuildBuildingPresenter(JPanel actionDialogBoxes, CardLayout cardLayout, TurnController controller) {
        this.controller = controller;
        this.actionDialogBoxes = actionDialogBoxes;

        this.choicesPanel = new JPanel();
        actionDialogBoxes.add(choicesPanel, "Choices Panel");
    }

    /**
     * Presents the list properties which would build a building.
     *
     * @param properties the property which would build a building or sell a building.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showBuildOption(ArrayList<ColorPropertyTile> properties, String flavorText){
        resetOptionsPanel();
        if (properties.size() == 0) {
            choicesPanel.add(new JLabel("You cannot build a building."));
            JButton optionsButton = new JButton("Back");
            optionsButton.addActionListener(e -> controller.endUseCase());
            choicesPanel.add(optionsButton);
            showOptionsPanel();
        } else {
            choicesPanel.add(new JLabel(flavorText));
            JComboBox<String> comboBox = new JComboBox<>();
            for (ColorPropertyTile property : properties){
                comboBox.addItem(property.getTileDisplayName());
            }
            choicesPanel.add(comboBox);
            JButton pickButton = new JButton("Pick");
            pickButton.addActionListener(e -> {
                for (ColorPropertyTile pickedProperty : properties){
                    if (pickedProperty.getTileDisplayName().equals(comboBox.getSelectedItem())){
                        if(pickedProperty.getNumHouses() < 4){
                        controller.buildHouse(pickedProperty);
                    } else {
                        controller.buildHotel(pickedProperty);
                    }
                    }
                }
            });
            choicesPanel.add(pickButton);
            JButton backButton = new JButton("Cancel");
            backButton.addActionListener(e -> controller.endUseCase());
            choicesPanel.add(backButton);
            showOptionsPanel();
        }
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
        endButton.addActionListener(e -> controller.endUseCase());
        choicesPanel.add(endButton);
        showOptionsPanel();
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
        endButton.addActionListener(e -> controller.endUseCase());
        choicesPanel.add(endButton);
        showOptionsPanel();
    }

    /**
     * Presents the list properties which would sell a building.
     *
     * @param properties the property which would build a building or sell a building.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showSellOption(ArrayList<ColorPropertyTile> properties, String flavorText){
        resetOptionsPanel();
        if (properties.size() == 0) {
            choicesPanel.add(new JLabel("You cannot sell a building."));
            JButton optionsButton = new JButton("Back");
            optionsButton.addActionListener(e -> controller.endUseCase());
            choicesPanel.add(optionsButton);
            showOptionsPanel();
        } else {
            choicesPanel.add(new JLabel(flavorText));
            JComboBox<String> comboBox = new JComboBox<>();
            for (ColorPropertyTile property : properties){
                comboBox.addItem(property.getTileDisplayName());
            }
            choicesPanel.add(comboBox);
            JButton pickButton = new JButton("Pick");
            pickButton.addActionListener(e -> {
                for (ColorPropertyTile pickedProperty : properties){
                    if (pickedProperty.getTileDisplayName().equals(comboBox.getSelectedItem())){
                        if(pickedProperty.getNumHotels() > 0){
                            controller.sellHotel(pickedProperty);
                        } else {
                            controller.sellHouse(pickedProperty);
                        }
                    }
                }
            });
            choicesPanel.add(pickButton);
            JButton backButton = new JButton("Cancel");
            backButton.addActionListener(e -> controller.endUseCase());
            choicesPanel.add(backButton);
            showOptionsPanel();
        }
    }

    private void resetOptionsPanel() {
        choicesPanel.removeAll();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.X_AXIS));
    }

    private void showOptionsPanel() {
        choicesPanel.revalidate();
        choicesPanel.repaint();
        CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
        cl.show(actionDialogBoxes, "Choices Panel");
    }
}
