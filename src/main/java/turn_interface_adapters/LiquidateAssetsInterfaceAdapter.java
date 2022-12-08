package turn_interface_adapters;

import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsOutputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquiditySituation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LiquidateAssetsInterfaceAdapter implements LiquidateAssetsOutputBoundary {

    private final TurnController turnController;
    private JPanel optionsPanel;
    private JLabel moneyTracker;
    private JFrame liquidatePopUp;
    private JPanel mainLiq;
    private CardLayout layoutLiq;
    private boolean visible = false;

    public LiquidateAssetsInterfaceAdapter(TurnController turnController){
        this.turnController = turnController;
        this.optionsPanel = new JPanel();
        this.moneyTracker = new JLabel();

        //new code
        liquidatePopUp = new JFrame("Liquidate Assets");
        liquidatePopUp.setMinimumSize(new Dimension(800, 400));
        mainLiq = new JPanel();
        layoutLiq = new CardLayout();
        mainLiq.setLayout(layoutLiq);
        mainLiq.add(optionsPanel, "Options Panel");
        liquidatePopUp.add(mainLiq);

    }


    @Override
    public void showPlayerOptions(ArrayList<String> playerOptions, LiquiditySituation situation) {
        this.resetPanel("These are your current options to avoid bankruptcy. You may declare Bankruptcy early if you wish to exit the game");
        for(int i = 0; i < playerOptions.size(); i++){
            if (playerOptions.get(i).equals("Pay Money")){
                JButton payButton = new JButton(playerOptions.get(i));
                payButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //This will need to be changed so that it goes through the controller?
                        visible = false;
                        liquidatePopUp.setVisible(visible);
                        situation.getAffectedPlayer().subtractMoney(situation.getOwedMoney());
                        if(situation.getOwedPlayer() != null) {
                            situation.getOwedPlayer().addMoney(situation.getOwedMoney());
                        }
                    }
                });
                optionsPanel.add(payButton);
            }
            if (playerOptions.get(i).equals("Trade")){
                JButton tradeButton = new JButton("Trade");
                tradeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        turnController.showTradablePlayers();
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
                        turnController.getMortgageableProperties(situation);
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
                        turnController.getPropertiesWithHouses(situation);
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
                        turnController.bankruptcy(situation);
                    }
                });
                optionsPanel.add(bankruptcyButton);
            }
        }
        JButton resetOptions = new JButton("Reset Options");
        resetOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnController.getPlayerOptions(situation);
            }
        });
        optionsPanel.add(resetOptions);
        moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
        optionsPanel.add(moneyTracker);
        this.showPanel();
    }

    @Override
    public void showMortgageableProperties(List<Property> mortgageableProperties, LiquiditySituation situation) {
        this.resetPanel("These are properties that you can mortgage to gain money:");
        //Making the various buttons that are the possible properties player can mortgage
        for(int i = 0; i < mortgageableProperties.size(); i++){
            Property property = mortgageableProperties.get(i);
            JButton mortgagePropertyButton = new JButton(property.getTileDisplayName() + ": $" + property.getMortgageValue());
            mortgagePropertyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    optionsPanel.remove(mortgagePropertyButton);
                    turnController.mortgageProperty(property);
                    optionsPanel.repaint();
                    moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
                }
            });
            optionsPanel.add(mortgagePropertyButton);
        }
        JButton stopMortgaging = new JButton("Cancel");
        stopMortgaging.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnController.getPlayerOptions(situation);
            }
        });
        optionsPanel.add(stopMortgaging);
        moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
        optionsPanel.add(moneyTracker);
        this.showPanel();
    }

    @Override
    public void showPropertiesWithHouses(List<ColorPropertyTile> propertiesWithHouses, LiquiditySituation situation) {
        this.resetPanel("These are properties that have houses that can be sold to gain money:");
        //Making the various buttons that are the properties that have houses that can be sold
        for(int i = 0; i < propertiesWithHouses.size(); i++){
            ColorPropertyTile property = propertiesWithHouses.get(i);
            JButton sellHouse = new JButton(property.getTileDisplayName() + ": $" + property.getBuildingCost() * 0.5);
            sellHouse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(0 < property.getNumHotels()){
                        turnController.sellHotel(property);
                    }
                    else {
                        turnController.sellHouse(property);
                    }
                    //Because selling a house on a property might change whether you can sell a house on another
                    // property, this method needs to be called again for the list to remain accurate
                    turnController.getPropertiesWithHouses(situation);
                }
            });
            optionsPanel.add(sellHouse);
        }
        JButton stopSelling = new JButton("Cancel");
        stopSelling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnController.getPlayerOptions(situation);
            }
        });
        optionsPanel.add(stopSelling);
        moneyTracker.setText("<html>Current Money: $" + situation.getAffectedPlayer().getMoney()+ "<br>Owed Money: $" + situation.getOwedMoney()+"</html>");
        optionsPanel.add(moneyTracker);
        this.showPanel();
    }

    @Override
    public void showTransferOfAssets(LiquiditySituation situation) {
        optionsPanel.removeAll();
        if(situation.getOwedPlayer() == null){
            optionsPanel.add(new JLabel("The bank bankrupted " +situation.getAffectedPlayer().getName()+ ". " +
                    "All of " +situation.getAffectedPlayer().getName()+ " properties and money have been recovered by the bank."));
        }
        else{
            optionsPanel.add(new JLabel(situation.getOwedPlayer().getName()+ " bankrupted " +situation.getAffectedPlayer().getName()+
                    ". " +situation.getOwedPlayer().getName()+ " now owns all of " +situation.getAffectedPlayer().getName()+ "'s properties and money."));
        }
        this.showPanel();
    }

    public void resetPanel(String jLabel){
        optionsPanel.removeAll();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JLabel(jLabel));
    }

    public void showPanel(){
        optionsPanel.validate();
        optionsPanel.repaint();
        liquidatePopUp.repaint();
        layoutLiq.show(mainLiq, "Options Panel");
        if (!visible) {
            visible = true;
            liquidatePopUp.setVisible(true);
        }
    }
}
