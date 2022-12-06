package turn_interface_adapters;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.Property;
import turn_use_cases.trade_use_case.TradeOffer;
import turn_use_cases.trade_use_case.TradeOption;
import turn_use_cases.trade_use_case.TradeOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TradePresenter implements TradeOutputBoundary {

    private final TurnController turnController;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final JPanel optionsPanel;

    public TradePresenter( JPanel mainPanel, CardLayout cardLayout){
        this.turnController = null;
        this.mainPanel = mainPanel;
        this.cardLayout =  cardLayout;
        this.optionsPanel = new JPanel();
        this.optionsPanel.setLayout(cardLayout);
        mainPanel.add(optionsPanel, "Options Panel");
    }



    /**
     * Presents the list of players player can trade with.
     *
     * @param listOfPlayers the list of players player can trade with.
     * @param player the player initiating the trade.
     * @param flavorText    the text describing what is happening.
     */
    @Override
    public void showListOfPlayers(ArrayList<Player> listOfPlayers, Player player, String flavorText) {
        optionsPanel.removeAll();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
        optionsPanel.add(new JLabel(flavorText + "      "));



        for (Player otherPlayer : listOfPlayers){
            JButton option = new JButton("Pick " + otherPlayer.getName());

            option.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    //turnController.startTrade(player, otherPlayer);

                }
            });
            optionsPanel.add(option);
        }

        optionsPanel.validate();
        optionsPanel.repaint();
        cardLayout.show(mainPanel, "Options Panel");
    }

    /**
     * Presents the possible options for player1 to trade with player2.
     *
     * @param tradeOption details the possible options for the trade
     * @param flavorText  the text describing what is happening.
     */
    @Override
    public void showTradeOptions(TradeOption tradeOption, String flavorText) {
        optionsPanel.removeAll();
        optionsPanel.setLayout(cardLayout);
        optionsPanel.add(new JLabel(flavorText));

        ArrayList<String> player1PropertiesDisplay = new ArrayList<>();
        ArrayList<Property> player1Properties = new ArrayList<>();


        optionsPanel.validate();
        optionsPanel.repaint();
        cardLayout.show(mainPanel, "Options Panel");
    }

    /**
     * Presents the trade offer.
     *
     * @param tradeOffer details what would happen during the trade.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showTradeOffer(TradeOffer tradeOffer, String flavorText) {

    }

    /**
     * Presents the result of the trade offer.
     *
     * @param option     the number indicating the result of the trade.
     *                   *               1 - player2 accepted the trade offer.
     *                   *               2 - player2 decided to make a counter offer.
     *                   *               3 - player2 declined the trade offer.
     *                   *               4 - player2's input was not valid and needs to try again.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showResultOfTradeOffer(int option, String flavorText) {

    }


}
