package turn_use_cases.trade_use_case;

import game_entities.Player;
import game_entities.tiles.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the functions related to the trading aspect of this game.
 */

public class TradeUseCase implements  TradeInputBoundary{
    private TradeOutputBoundary presenter;

    /**
     * Creates an instance of the trade class with the provided presenter.
     *
     * @param presenter displays the result of the functions in this class to the screen.
     */
    public TradeUseCase(TradeOutputBoundary presenter){
        this.presenter = presenter;
    }


    /**
     * Provides a list of all the other players in the game player can trade with to the presenter.
     *
     * @param listOfPlayers list of all the players in the game.
     * @param player        the player who wants to make a trade.
     */
    @Override
    public void choosePlayer(List<Player> listOfPlayers, Player player) {


        ArrayList<Player> listOfPotentialPlayers = new ArrayList<>(listOfPlayers);
        listOfPotentialPlayers.remove(player);


        presenter.showListOfPlayers(listOfPotentialPlayers, player,
                player.getName() +  ", please choose who to trade with.");


    }


    /**
     * Provides the presenter with all potential options for player1 and player2 to trade.
     *
     * @param player1 the player who wants to make a trade
     * @param player2 the player who player1 wants to trade with
     */
    @Override
    public void getTradeOptions(Player player1, Player player2) {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                player1.getProperties(), player2.getProperties(), player1, player2);

        presenter.showTradeOptions(tradeOption,"<html><body>"+ player1.getName() +
                ", please choose what you want to trade."+"</body></html>");

    }


    /**
     * Provides the presenter with the details of the trade offer and whether it is valid.
     *
     * @param tradeOffer the trade offer player1 wants to make with player 2
     * @param player1 the player making the trade offer
     * @param player2 the player receiving the trade offer
     */
    @Override
    public void makeOffer(TradeOffer tradeOffer, Player player1, Player player2) {
        if(tradeOffer.isValid()){
            presenter.showTradeOffer(tradeOffer, player2.getName() + ", do you accept this trade?");
        } else {
            presenter.showTradeOffer(tradeOffer, player1.getName() +
                    ", this is an invalid trade, please make another offer.");
        }



    }

    /**
     * Provides the presenter with the result of the trade and rearranges player1's and player 2's inventory
     * if the trade was successful.
     *
     * @param option the number indicating the result of the trade.
     *               1 - player2 accepted the trade offer.
     *               2 - player2 decided to make a counter offer.
     *               3 - player2 declined the trade offer.
     *               4 - player2's input was not valid and needs to try again.
     * @param player1 the player who started the trade offer.
     * @param player2 the player who received the trade offer.
     * @param tradeOffer the details of the trade.
     */
    @Override
    public void getResultOfTradeOffer(int option, Player player1, Player player2, TradeOffer tradeOffer) {

        if(option == 1){
            ExecuteOffer(player1, player2, tradeOffer);
            presenter.showResultOfTradeOffer(1, "The Trade was a success!", player1 , player2);
        } else if (option == 2) {
            presenter.showResultOfTradeOffer(2, player2.getName() + "wants to make a counter offer!", player1,  player2);
        } else if (option == 3) {
            presenter.showResultOfTradeOffer(3, "The offer was declined!", player1, player2);
        } else{
            presenter.showResultOfTradeOffer(4, "That was an invalid input, please try again.", player1 , player2);
        }

    }

    /**
     * Rearranges player1's and player2's inventory depending on tradeOffer.
     *
     * @param player1 the player who started the trade offer.
     * @param player2 the player who received the trade offer.
     * @param tradeOffer the details of the trade.
     */
    public void ExecuteOffer(Player player1, Player player2, TradeOffer tradeOffer){
        player1.subtractMoney(tradeOffer.getTradeMoney());
        player2.addMoney(tradeOffer.getTradeMoney());

        for (Property p : tradeOffer.getPropertiesReceived()){
            player1.addProperty(p);
            player2.sellProperty(p);
        }

        for (Property p : tradeOffer.getPropertiesOffered()){
            player1.sellProperty(p);
            player2.addProperty(p);
        }

        if (tradeOffer.getJailCard() > 0){
            player1.removeGetOutOfJailCard();
            player2.addGetOutOfJailCard();
        } else if (tradeOffer.getJailCard() < 0) {
            player1.addGetOutOfJailCard();
            player2.removeGetOutOfJailCard();
        }

    }


}