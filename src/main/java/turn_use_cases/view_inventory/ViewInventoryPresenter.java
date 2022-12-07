package turn_use_cases.view_inventory;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.RailroadTile;
import game_entities.tiles.UtilityTile;
import turn_interface_adapters.TurnController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewInventoryPresenter implements ViewInventoryOutputBoundary {

    private final JPanel inventorySummaryBox;
    private final List<Player> playersInfo;
    private final TurnController tc;

    public ViewInventoryPresenter(JPanel inventorySummaryBox, List<Player> playersInfo, TurnController turnController) {
        this.inventorySummaryBox = inventorySummaryBox;
        this.playersInfo = playersInfo;
        this.tc = turnController;
        showInventoryButtons();
    }

    @Override
    public void showInventory(String currentPlayer, List<InventoryData> playersInfo) {
        InventoryData player = playersInfo.get(0);
        for (InventoryData x : playersInfo) {
            if (Objects.equals(x.getName(), currentPlayer)) {
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
                        .getImage().getScaledInstance((int) (250), (int) (320), Image.SCALE_SMOOTH));
                currentPropertySetPanel.add(new JLabel(temp));
            }
            inventoryInfo.add(currentPropertySetPanel);
        }
    }

    @Override
    public void showInventoryButtons() {
        GridLayout quad = new GridLayout(2, 2);
        inventorySummaryBox.setLayout(quad);
        JPanel player1 = new JPanel();
        JPanel player2 = new JPanel();
        JPanel player3 = new JPanel();
        JPanel player4 = new JPanel();
        JButton player1Button = new JButton("View " + playersInfo.get(0).getName() + "s' Inventory");
        JButton player2Button = new JButton("View " + playersInfo.get(1).getName() + "s' Inventory");
        JButton player3Button = new JButton("View " + playersInfo.get(2).getName() + "s' Inventory");
        JButton player4Button = new JButton("View " + playersInfo.get(3).getName() + "s' Inventory");
        player1.setBackground(new Color(44,168,219));
        player2.setBackground(new Color(219, 212, 63));
        player3.setBackground(new Color(219, 42, 74));
        player4.setBackground(new Color(143, 34, 54));
        player1.add(player1Button);
        player2.add(player2Button);
        player3.add(player3Button);
        player4.add(player4Button);
        JLabel player1Money = new JLabel("Money: " + playersInfo.get(0).getMoney());
        JLabel player2Money = new JLabel("Money: " + playersInfo.get(0).getMoney());
        JLabel player3Money = new JLabel("Money: " + playersInfo.get(0).getMoney());
        JLabel player4Money = new JLabel("Money: " + playersInfo.get(0).getMoney());
        player1.add(player1Money);
        player2.add(player2Money);
        player3.add(player3Money);
        player4.add(player4Money);
        JLabel gojCardP1 = new JLabel("Get out of Jail Free:" + playersInfo.get(0).numGetOutofJailFreeCards());
        JLabel gojCardP2 = new JLabel("Get out of Jail Free:" + playersInfo.get(1).numGetOutofJailFreeCards());
        JLabel gojCardP3 = new JLabel("Get out of Jail Free:" + playersInfo.get(2).numGetOutofJailFreeCards());
        JLabel gojCardP4 = new JLabel("Get out of Jail Free:" + playersInfo.get(3).numGetOutofJailFreeCards());
        player1.add(gojCardP1);
        player2.add(gojCardP2);
        player3.add(gojCardP3);
        player4.add(gojCardP4);
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
