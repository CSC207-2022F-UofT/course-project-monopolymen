package turn_interface_adapters;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import turn_use_cases.build_use_case.BuildBuildingInputBoundary;
import turn_use_cases.mortgage_use_case.MortgagePropertyInputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerInputBoundary;
import turn_use_cases.trade_use_case.TradeInputBoundary;
import turn_use_cases.trade_use_case.TradeOffer;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailInputBoundary;
import turn_use_cases.view_inventory.ViewInventoryInputBoundary;

import java.util.ArrayList;
import java.util.List;

/**
 * Interacts with each Turn use case as appropriate through the use case's
 * InputBoundary interface.
 * The Turn Controller is responsible for formatting the input data (if applicable) to the
 * InputBoundary's specification.
 * The Turn Controller also performs delegation to the simpler use case methods.
 */
public class TurnController {
    private final BuildBuildingInputBoundary buildBuilding;
    private final MortgagePropertyInputBoundary mortgageProperty;
    private final MovePlayerInputBoundary movePlayer;
    private final TradeInputBoundary trade;
    private final TryToGetOutOfJailInputBoundary getOutOfJail;
    private final ViewInventoryInputBoundary viewInventory;
    private final GameState gameState;
    private EndUseCaseDestination nextEndUseCaseDestination;

    public TurnController(BuildBuildingInputBoundary buildBuilding,
                          MortgagePropertyInputBoundary mortgageProperty,
                          MovePlayerInputBoundary movePlayer,
                          TradeInputBoundary trade,
                          TryToGetOutOfJailInputBoundary getOutOfJail,
                          ViewInventoryInputBoundary viewInventory,
                          GameState gameState) {
        this.buildBuilding = buildBuilding;
        this.mortgageProperty = mortgageProperty;
        this.movePlayer = movePlayer;
        this.trade = trade;
        this.getOutOfJail = getOutOfJail;
        this.viewInventory = viewInventory;
        this.gameState = gameState;
        this.nextEndUseCaseDestination = EndUseCaseDestination.DEFAULT_DESTINATION;
    }

    /**
     * Signifies the end of a use case. The action taken depends on what is set as the nextEndUseCaseDestination
     * ({@link #setNextEndUseCaseDestination(EndUseCaseDestination) see setter for details }). After this method is called,
     * the nextEndUseCaseDestination is reset to the DEFAULT_DESTINATION.
     */
    public void endUseCase() {
        switch (nextEndUseCaseDestination) {
            case DEFAULT_DESTINATION:
                gameState.showTurnActions();
                break;
            case LIQUIDATE_ASSETS:
                //TODO call to the liquidate assets use case (does not exist yet)
                break;
        }
        nextEndUseCaseDestination = EndUseCaseDestination.DEFAULT_DESTINATION;
    }

    public EndUseCaseDestination getNextEndUseCaseDestination() {
        return nextEndUseCaseDestination;
    }

    /**
     * Sets which method will be called after the next endUseCase method is called. Destinations:
     * <ul>
     *     <li><b>DEFAULT_DESTINATION</b> The endUseCase method will show the player a list of turn action options
     *          they can take.</li>
     *     <li><b>LIQUIDATE_ASSETS</b> The endUseCase method will call the LIQUIDATE_ASSETS use case (to be used
     *          when a use case is called from the LiquidateAssets use case when the player must get money to make
     *          a payment.</li>
     * </ul>
     */
    public void setNextEndUseCaseDestination(EndUseCaseDestination nextEndUseCaseDestination) {
        this.nextEndUseCaseDestination = nextEndUseCaseDestination;
    }

    public void endTurn() {
        gameState.endTurn();
    }

    /* Move Player/RollDice use case related methods */
    public void rollDice(Player player) {
        movePlayer.startAction(player);
    }

    /* TryToGetOutOfJail use case related methods */
    public void attemptLeaveJail(Player player) {
        // Show the player their options for getting out of jail
        getOutOfJail.getPlayerOptions(player);
    }

    public void leaveJailWithChoice(Player player, String playerChoice) {
        // Leave jail with the appropriate option.
        getOutOfJail.startAction(playerChoice, player);
    }

    /* NOTE Mortgage property related methods */
    public void mortgageProperty(Player player, Property property) {
        mortgageProperty.mortgage(player, property);
    }

    public void unmortgageProperty(Player player, Property property) {
        mortgageProperty.unmortgage(player, property);
    }

    /* BuildBuilding Related Methods */
    public void getBuildableProperties() {
        // TODO call the method in the BuildBuildingInputBoundary to show the list of properties
        //      the player can build on. (Does not exist right now)
    }

    public void buildHouse(Player player, ColorPropertyTile property) {
        buildBuilding.buildHouse(player, property);
    }

    public void buildHotel(Player player, ColorPropertyTile property) {
        buildBuilding.buildHotel(player, property);
    }

    public void getBuiltOnProperties() {
        // TODO call the method in the BuildBuildingInputBoundary to show the list of properties
        //      that have a house/hotel on them to sell. (Does not exist right now)
    }

    public void sellHouse(Player player, ColorPropertyTile property) {
        buildBuilding.sellHouse(player, property);
    }

    public void sellHotel(Player player, ColorPropertyTile property) {
        buildBuilding.sellHotel(player, property);
    }

    /* Trade Related Methods */
    public void showTradablePlayers(Player currentPlayer, List<Player> playerList) {
        trade.choosePlayer((ArrayList<Player>) playerList, currentPlayer);
    }

    public void startTrade(Player proposing, Player receiving) {
        trade.getTradeOptions(proposing, receiving);
    }

    public void makeOffer(Player offering, Player receiving, int player1Money, int player2Money, int player1JailCard,
                          int player2JailCard, List<Property> player1Property, List<Property> player2Property) {
        TradeOffer player1Offer = new TradeOffer(player1Money - player2Money, player1JailCard - player2JailCard,
                (ArrayList<Property>) player1Property, (ArrayList<Property>) player2Property, offering, receiving);
        trade.makeOffer(player1Offer, offering, receiving);
    }

    public void acceptTradeOffer(Player offering, Player receiving, TradeOffer tradeOffer) {
        trade.getResultOfTradeOffer(1, offering, receiving, tradeOffer);
    }

    public void declineTradeOffer(Player offering, Player receiving, TradeOffer tradeOffer) {
        trade.getResultOfTradeOffer(3, offering, receiving, tradeOffer);
    }

    public void counterOffer(Player offering, Player receiving) {
        // The receiving player is now making the offer
        trade.getTradeOptions(receiving, offering);
    }

    /* ViewInventory Related Methods */
    public void showInventory(Player currentPlayer, List<Player> playerList) {
        viewInventory.displayInfo(currentPlayer, playerList);
    }

    enum EndUseCaseDestination {
        DEFAULT_DESTINATION,
        LIQUIDATE_ASSETS
    }
}
