package TurnUseCases.TradeUseCase;

import GameEntities.Player;

import java.util.ArrayList;

/**
 * The input boundary for the trade use case.
 */

public interface TradeInputBoundary {


    /**
     * Provides a list of all the other players in the game player can trade with to the presenter.
     *
     * @param listOfPlayers list of all the players in the game.
     * @param player        the player who wants to make a trade.
     * @return the list of potential players
     * (return only used for testing, will remove later when presenter is implemented.)
     */
    public ArrayList<Player> choosePlayer(ArrayList<Player> listOfPlayers, Player player);

    /**
     * Provides the presenter with all potential options for player1 and player2 to trade.
     *
     * @param player1 the player who wants to make a trade
     * @param player2 the player who player1 wants to trade with
     * @return a TradeOption object that contains the potential options for the trade.
     * (return only used for testing, will remove later when presenter is implemented.)
     */
    public TradeOption getTradeOptions(Player player1, Player player2);

    /**
     * Provides the presenter with the details of the trade offer and whether it is valid.
     *
     * @param tradeOffer the trade offer player1 wants to make with player 2
     * @param player1 the player making the trade offer
     * @param player2 the player receiving the trade offer
     */
    public void makeOffer(TradeOffer tradeOffer, Player player1, Player player2);

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
    public void getResultOfTradeOffer(int option, Player player1, Player player2, TradeOffer tradeOffer);





}