package turn_use_cases.view_inventory;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.RailroadTile;
import game_entities.tiles.UtilityTile;
import turn_interface_adapters.TurnController;
import turn_use_cases.view_inventory.InventoryData;
import turn_use_cases.view_inventory.ViewInventoryOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewInventoryPresenter implements ViewInventoryOutputBoundary {

    private final JPanel chosenPlayerSummaryBox = new JPanel();
    @Override
    public void showInventory(String currentPlayer, List<InventoryData> playersInfo) {
        InventoryData player = playersInfo.get(0);
        for (InventoryData x : playersInfo){
            if (Objects.equals(x.getName(), currentPlayer)){
                player = x;
            }
        }
        JFrame popUpInventory = new JFrame(currentPlayer + " s' Inventory");
        popUpInventory.setMinimumSize(new Dimension(1000, 390));
        JPanel inventoryInfo = new JPanel();
        JScrollPane testing = new JScrollPane();
        testing.setViewportView(inventoryInfo);
        popUpInventory.add(testing);
        inventoryInfo.add(new JLabel(currentPlayer + " s' Inventory"));
        JButton closeTab = new JButton("Close Inventory");
        popUpInventory.setVisible(true);
        inventoryInfo.add(closeTab);
        closeTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpInventory.setVisible(false);
            }
        });
        TextField money = new TextField("Money: " + String.valueOf(player.getMoney()));
        money.setEditable(false);
        inventoryInfo.add(money);
        List<List<Property>> displayProperties = new ArrayList<List<Property>>();
        displayProperties = InventoryData.sortProperties(player);
        for (List<Property> currentPropertyList : displayProperties){
            JPanel currentPropertySetPanel = new JPanel();
            for(Property currentProperty : currentPropertyList){
                String frontOrBack = "front";
                if (currentProperty.isMortgaged()){
                    frontOrBack = "mortgaged";
                }
                String id = "";
                if (currentProperty instanceof UtilityTile){
                    id = "utility_" + currentProperty.getTileDisplayName() +".jpg";
                } else if (currentProperty instanceof RailroadTile) {
                    id = "rr_" + currentProperty.getTileDisplayName() + ".jpg";
                } else {
                    ColorPropertyTile newTemp = (ColorPropertyTile) currentProperty;
                    id = newTemp.getColor().toLowerCase() + "_" + newTemp.getTileDisplayName() + ".jpg";
                }
                String path = "src/main/resources/assets/property/property_" + frontOrBack + "_" + id;
                System.out.println(path);
                ImageIcon temp = new ImageIcon(new ImageIcon
                        (path)
                        .getImage().getScaledInstance((int)(250), (int)(320), Image.SCALE_SMOOTH));
                currentPropertySetPanel.add(new JLabel(temp));
            }
            inventoryInfo.add(currentPropertySetPanel);
        }
    }
    @Override
    public void showInventoryButtons(List<Player> playersInfo, TurnController tc, JPanel inventorySummaryBox) {
        GridLayout quad = new GridLayout(2,2);
        inventorySummaryBox.setLayout(quad);
        JPanel player1 = new JPanel();
        JPanel player2 = new JPanel();
        JPanel player3 = new JPanel();
        JPanel player4 = new JPanel();
        JButton player1Button = new JButton("View "+ playersInfo.get(0).getName() + "s' Inventory");
        JButton player2Button = new JButton("View "+ playersInfo.get(1).getName() + "s' Inventory");
        JButton player3Button = new JButton("View "+ playersInfo.get(2).getName() + "s' Inventory");
        JButton player4Button = new JButton("View "+ playersInfo.get(3).getName() + "s' Inventory");
        player1.setBackground(new Color(201, 201, 201));
        player2.setBackground(new Color(158, 158, 158));
        player3.setBackground(new Color(125, 125, 125));
        player4.setBackground(new Color(107, 106, 106));
        player1.add(player1Button);
        player2.add(player2Button);
        player3.add(player3Button);
        player4.add(player4Button);
        player1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tc.showInventory(playersInfo.get(0), playersInfo);
            }
        });
        player2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tc.showInventory(playersInfo.get(1), playersInfo);
            }
        });
        player3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tc.showInventory(playersInfo.get(2), playersInfo);
            }
        });
        player3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tc.showInventory(playersInfo.get(3), playersInfo);
            }
        });
        inventorySummaryBox.add(player1);
        inventorySummaryBox.add(player2);
        inventorySummaryBox.add(player3);
        inventorySummaryBox.add(player4);
        inventorySummaryBox.setVisible(true);

    }

}