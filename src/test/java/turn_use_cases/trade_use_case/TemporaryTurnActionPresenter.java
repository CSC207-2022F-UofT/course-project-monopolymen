package turn_use_cases.trade_use_case;

import game_entities.Player;

import java.util.ArrayList;

public class TemporaryTurnActionPresenter implements  TradeOutputBoundary{

    public ArrayList<Player> testPlayerList = null;

    public TradeOption testTradeOption = null;

    public TradeOffer testTradeOffer = null;

    public int testOption = 0;


    /**
     * Presents the list of players player can trade with.
     *
     * @param listOfPlayers the list of players player can trade with.
     * @param flavorText    the text describing what is happening.
     */
    @Override
    public void showListOfPlayers(ArrayList<Player> listOfPlayers, Player player, String flavorText) {
        this.testPlayerList = listOfPlayers;

    }

    /**
     * Presents the possible options for player1 to trade with player2.
     *
     * @param tradeOption details the possible options for the trade
     * @param flavorText  the text describing what is happening.
     */
    @Override
    public void showTradeOptions(TradeOption tradeOption, String flavorText) {
        this.testTradeOption = tradeOption;

    }

    /**
     * Presents the trade offer.
     *
     * @param tradeOffer details what would happen during the trade.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showTradeOffer(TradeOffer tradeOffer, String flavorText) {
        this.testTradeOffer = tradeOffer;
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
     * @param player1
     * @param player2
     */
    @Override
    public void showResultOfTradeOffer(int option, String flavorText, Player player1, Player player2) {
        this.testOption = option;

    }
}
