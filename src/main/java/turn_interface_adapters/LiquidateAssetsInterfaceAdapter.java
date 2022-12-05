package turn_interface_adapters;

import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsOutputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsUseCase;
import turn_use_cases.liquidate_assets_use_case.LiquiditySituation;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LiquidateAssetsInterfaceAdapter implements LiquidateAssetsOutputBoundary {

    private final TurnController turnController;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    private JPanel optionsPanel;
    private JLabel moneyTracker;

    public LiquidateAssetsInterfaceAdapter(TurnController turnController, JPanel mainPanel, CardLayout cardLayout){
        this.turnController = turnController;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.optionsPanel = new JPanel();
        this.moneyTracker = new JLabel();
        mainPanel.add(optionsPanel, "Options Panel");

    }


    @Override
    public void showPlayerOptions(ArrayList<String> playerOptions, LiquiditySituation situation) {
        optionsPanel.removeAll();
        LiquidateAssetsInterfaceAdapter laia = this;
        //This is the main panel for the use case. BoxLayout so that the options come out as a list
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JLabel("These are your current options to avoid bankruptcy. " +
                "You may declare Bankruptcy early if you wish to exit the game"));
        //Making the various buttons that are the possible player options.
        for(int i = 0; i < playerOptions.size(); i++){
            if (playerOptions.get(i).equals("Pay Money")){
                JButton payButton = new JButton(playerOptions.get(i));
                payButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //This will need to be changed so that it goes through the controller?
                        situation.getAffectedPlayer().subtractMoney(situation.getOwedMoney());
                        situation.getOwedPlayer().addMoney(situation.getOwedMoney());
                    }
                });
                optionsPanel.add(payButton);
            }
            if (playerOptions.get(i).equals("Trade")){
                JButton tradeButton = new JButton("Trade");
                tradeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        turnController.showTradablePlayers(situation.getAffectedPlayer(), situation.getGameState().getPlayers());
                    }
                });
                optionsPanel.add(tradeButton);
            }
            if (playerOptions.get(i).equals("Mortgage Property")){
                JButton mortgageButton = new JButton("Mortgage Property");
                mortgageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //This will need to be changed so that it goes through the controller
                        LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                        liquidateAssetsUseCase.getMortgageableProperties(situation);


                    }
                });
                optionsPanel.add(mortgageButton);
            }
            if (playerOptions.get(i).equals("Sell Houses/Hotels")){
                JButton sellHousesButton = new JButton("Sell Houses/Hotels");
                sellHousesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //This will need to be changed so that it goes through the controller
                        LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                        liquidateAssetsUseCase.getPropertiesWithHouses(situation);
                    }
                });
                optionsPanel.add(sellHousesButton);
            }
            if (playerOptions.get(i).equals("Declare Bankruptcy")){
                JButton bankruptcyButton = new JButton("Declare Bankruptcy");
                bankruptcyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //This will need to be changed so that it goes through the controller
                        LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                        liquidateAssetsUseCase.bankruptcy(situation);
                    }
                });
                optionsPanel.add(bankruptcyButton);
            }
        }
        moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
        optionsPanel.add(moneyTracker);
        optionsPanel.validate();
        optionsPanel.repaint();
        cardLayout.show(mainPanel, "Options Panel");
    }

    @Override
    public void showMortgageableProperties(List<Property> mortgageableProperties, LiquiditySituation situation) {
        optionsPanel.removeAll();
        LiquidateAssetsInterfaceAdapter laia = this;
        //This is the main panel for this method.
        //This will make the mortgage buttons come out as a list.
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JLabel("These are properties that you can mortgage to gain money:"));
        JScrollPane buttonList = new JScrollPane();
        optionsPanel.add(buttonList);
        //Making the various buttons that are the possible properties player can mortgage
        for(int i = 0; i < mortgageableProperties.size(); i++){
            Property property = mortgageableProperties.get(i);
            JButton mortgagePropertyButton = new JButton(property.getTileDisplayName() + ": $" + property.getMortgageValue());
            mortgagePropertyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    optionsPanel.remove(mortgagePropertyButton);
                    optionsPanel.repaint();
                    turnController.mortgageProperty(situation.getAffectedPlayer(), property);
                }
            });
            buttonList.add(mortgagePropertyButton);
        }
        JButton stopMortgaging = new JButton("Cancel");
        stopMortgaging.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                liquidateAssetsUseCase.getPlayerOptions(situation);
            }
        });
        buttonList.add(stopMortgaging);
        moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
        optionsPanel.add(moneyTracker);
        optionsPanel.validate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "Options Panel");
    }

    @Override
    public void showPropertiesWithHouses(List<ColorPropertyTile> propertiesWithHouses, LiquiditySituation situation) {
        optionsPanel.removeAll();
        LiquidateAssetsInterfaceAdapter laia = this;
        //This is the main panel for this method.
        //Hopefully this will make the sell house buttons come out as a list.
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JLabel("These are properties that have houses that can be sold to gain money:"));
        JScrollPane buttonList = new JScrollPane();
        optionsPanel.add(buttonList);
        //Making the various buttons that are the properties that have houses that can be sold
        for(int i = 0; i < propertiesWithHouses.size(); i++){
            ColorPropertyTile property = propertiesWithHouses.get(i);
            JButton sellHouse = new JButton(property.getTileDisplayName() + ": $" + property.getBuildingCost() * 0.5);
            sellHouse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(0 < property.getNumHotels()){
                        turnController.sellHotel(situation.getAffectedPlayer(), property);
                    }
                    else {
                        turnController.sellHouse(situation.getAffectedPlayer(), property);
                    }
                    //Because selling a house on a property might change whether you can sell a house on another
                    // property, this method needs to be called again for the list to remain accurate
                    LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                    liquidateAssetsUseCase.getPlayerOptions(situation);
                }
            });
            buttonList.add(sellHouse);
        }
        JButton stopSelling = new JButton("Cancel");
        stopSelling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                liquidateAssetsUseCase.getPlayerOptions(situation);
            }
        });
        buttonList.add(stopSelling);
        moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
        optionsPanel.add(moneyTracker);
        optionsPanel.validate();
        optionsPanel.repaint();
        cardLayout.show(mainPanel, "Options Panel");
    }

    @Override
    public void showTransferOfAssets(LiquiditySituation situation) {
        optionsPanel.removeAll();
        if(situation.getOwedPlayer() == null){
            optionsPanel.add(new JLabel("The bank bankrupted " +situation.getAffectedPlayer()+ ". " +
                    "All of " +situation.getAffectedPlayer()+ " properties and money have been recovered by the bank."));
        }
        else{
            optionsPanel.add(new JLabel(situation.getOwedPlayer().getName()+ " bankrupted " +situation.getAffectedPlayer().getName()+
                    ". " +situation.getOwedPlayer().getName()+ " now owns all of " +situation.getAffectedPlayer().getName()+ "'s properties and money."));
        }
        optionsPanel.validate();
        optionsPanel.repaint();
        cardLayout.show(mainPanel, "Options Panel");
    }
}
