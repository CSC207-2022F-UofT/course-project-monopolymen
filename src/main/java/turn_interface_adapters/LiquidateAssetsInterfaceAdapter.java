package turn_interface_adapters;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import turn_use_cases.build_use_case.BuildBuildings;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsOutputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsUseCase;
import turn_use_cases.liquidate_assets_use_case.LiquiditySituation;
import turn_use_cases.mortgage_use_case.MortgageProperty;
import turn_use_cases.trade_use_case.TradeOutputBoundary;
import turn_use_cases.trade_use_case.TradeUseCase;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LiquidateAssetsInterfaceAdapter implements LiquidateAssetsOutputBoundary {

    LiquidateAssetsInterfaceAdapter(){}

    @Override
    public void showPlayerOptions(ArrayList<String> playerOptions, LiquiditySituation situation) {
        LiquidateAssetsInterfaceAdapter laia = this;
        //This is the main panel for the use case. FlowLayout (default layout) should be fine?
        JPanel playerOptionDialogueBox = new JPanel();
        playerOptionDialogueBox.add(new JLabel("These are your current options to avoid bankruptcy. " +
                "You may declare Bankruptcy early if you wish to exit the game"));
        //Making the various buttons that are the possible player options.
        for(int i = 0; i < playerOptions.size(); i++){
            if (playerOptions.get(i).equals("Pay Money")){
                JButton payButton = new JButton(playerOptions.get(i));
                payButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        situation.getAffectedPlayer().subtractMoney(situation.getOwedMoney());
                        situation.getOwedPlayer().addMoney(situation.getOwedMoney());
                    }
                });
                playerOptionDialogueBox.add(payButton);
            }
            if (playerOptions.get(i).equals("Trade")){
                JButton tradeButton = new JButton("Trade");
                tradeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TradeInterfaceAdapter tradePresenter = new TradeInterfaceAdapter();
                        TradeUseCase tradeUseCase = new TradeUseCase(tradePresenter);
                        ArrayList<Player> players = new ArrayList<Player>(situation.getGameState().getPlayers());
                        tradeUseCase.choosePlayer(players, situation.getAffectedPlayer());
                        //might have to remove the current panels, although TradeUseCase might need something that
                        // knows it should call LiquidateAssetsUseCase.getPlayerOptions after the trade is done
                    }
                });
                playerOptionDialogueBox.add(tradeButton);
            }
            if (playerOptions.get(i).equals("Mortgage Property")){
                JButton mortgageButton = new JButton("Mortgage Property");
                mortgageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LiquidateAssetsUseCase liquid_a_use_case = new LiquidateAssetsUseCase(laia);
                        liquid_a_use_case.getMortgageableProperties(situation);
                        //Might have to add something that gets rid of this presenters panels as this method call
                        // another presenter method
                    }
                });
                playerOptionDialogueBox.add(mortgageButton);
            }
            if (playerOptions.get(i).equals("Sell Houses/Hotels")){
                JButton sellHousesButton = new JButton("Sell Houses/Hotels");
                sellHousesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LiquidateAssetsUseCase liquid_a_use_case = new LiquidateAssetsUseCase(laia);
                        liquid_a_use_case.getPropertiesWithHouses(situation);
                        //Might have to add something that gets rid of this presenters panels as this method call
                        // another presenter method
                    }
                });
                playerOptionDialogueBox.add(sellHousesButton);
            }
            if (playerOptions.get(i).equals("Declare Bankruptcy")){
                JButton bankruptcyButton = new JButton("Declare Bankruptcy");
                bankruptcyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                        liquidateAssetsUseCase.bankruptcy(situation);
                        //Might have to add something that gets rid of this presenters panels as this method call
                        // another presenter method
                    }
                });
                playerOptionDialogueBox.add(bankruptcyButton);
            }
        }
        //need to add this jpanel to the main window/jpanel

    }

    @Override
    public void showMortgageableProperties(List<Property> mortgageableProperties, LiquiditySituation situation) {
        LiquidateAssetsInterfaceAdapter laia = this;
        //This is the main panel for this method.
        JPanel mortgageOptionsDialogueBox = new JPanel();
        //Hopefully this will make the mortgage buttons come out as a list.
        mortgageOptionsDialogueBox.setLayout(new BoxLayout(mortgageOptionsDialogueBox, BoxLayout.Y_AXIS));
        mortgageOptionsDialogueBox.add(new JLabel("These are properties that you can mortgage to gain money."));
        //Making the various buttons that are the possible properties player can mortgage
        for(int i = 0; i < mortgageableProperties.size(); i++){
            Property property = mortgageableProperties.get(i);
            JButton mortgagePropertyButton = new JButton(property.getTileDisplayName());
            mortgagePropertyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MortgagePropertyInterfaceAdapter mortgagePresenter = new MortagePropertyInterfaceAdapter();
                    MortgageProperty mortgageProperty = new MortgageProperty(mortgagePresenter);
                    mortgageProperty.mortgage(situation.getAffectedPlayer(), property);
                }
            });
            mortgageOptionsDialogueBox.add(mortgagePropertyButton);
        }
        JButton stopMortgaging = new JButton("Cancel");
        stopMortgaging.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                liquidateAssetsUseCase.getPlayerOptions(situation);
                //may need to add something that removes this JPanel
            }
        });
        //need to add this jpanel to the main window/jpanel

    }

    @Override
    public void showPropertiesWithHouses(List<ColorPropertyTile> propertiesWithHouses, LiquiditySituation situation) {
        LiquidateAssetsInterfaceAdapter laia = this;
        //This is the main panel for this method.
        JPanel sellHousesOptionsDialogueBox = new JPanel();
        //Hopefully this will make the sell house buttons come out as a list.
        sellHousesOptionsDialogueBox.setLayout(new BoxLayout(sellHousesOptionsDialogueBox, BoxLayout.Y_AXIS));
        sellHousesOptionsDialogueBox.add(new JLabel("These are properties that have houses that can be sold to gian money."));
        //Making the various buttons that are the properties that have houses that can be sold
        for(int i = 0; i < propertiesWithHouses.size(); i++){
            ColorPropertyTile property = propertiesWithHouses.get(i);
            JButton sellHouse = new JButton(property.getTileDisplayName() + ", " + property.getBuildingCost() * 0.5);
            sellHouse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BuildBuildingsInterfaceAdapter buildBuildingsPresenter = new BuildBuildingsInterfaceAdapter();
                    BuildBuildings buildBuildings = new BuildBuildings(buildBuildingsPresenter, situation.getBoard());
                    if(0 < property.getNumHotels()){
                        buildBuildings.sellHotel(situation.getAffectedPlayer(), property);
                    }
                    else {
                        buildBuildings.sellHouse(situation.getAffectedPlayer(), property);
                    }
                    //Because selling a house on a property might change whether you can sell a house on another
                    // property, this method needs to be called again for the list to remain accurate
                    LiquidateAssetsUseCase liquidateAssetsUseCase = new LiquidateAssetsUseCase(laia);
                    liquidateAssetsUseCase.getPlayerOptions(situation);
                    //Might need to remove this panel as above function calls a presenter.
                }
            });
            sellHousesOptionsDialogueBox.add(sellHouse);
        }
        //will need to add this panel to the main window/panel
    }

    @Override
    public void showTransferOfAssets(LiquiditySituation situation) {
        JPanel bankrupted = new JPanel();
        if(situation.getOwedPlayer() == null){
            bankrupted.add(new JLabel("The bank bankrupted " +situation.getAffectedPlayer()+ ". " +
                    "All of " +situation.getAffectedPlayer()+ " properties and money have been recovered by the bank."));
        }
        else{
            bankrupted.add(new JLabel(situation.getOwedPlayer().getName()+ " bankrupted " +situation.getAffectedPlayer().getName()+
                    ". " +situation.getOwedPlayer().getName()+ " now owns all of " +situation.getAffectedPlayer().getName()+ "'s properties and money."));
        }
        //will need to add this panel to the main window/panel

    }
}
