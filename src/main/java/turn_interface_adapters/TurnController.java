package turn_interface_adapters;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import turn_use_cases.build_use_case.BuildBuildingInputBoundary;
import turn_use_cases.end_turn_use_case.EndTurnInputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsInputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquiditySituation;
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
    private final GameState gameState;
    private BuildBuildingInputBoundary buildBuilding;
    private MortgagePropertyInputBoundary mortgageProperty;
    private MovePlayerInputBoundary movePlayer;
    private TradeInputBoundary trade;
    private TryToGetOutOfJailInputBoundary getOutOfJail;
    private ViewInventoryInputBoundary viewInventory;
    private EndUseCaseDestination nextEndUseCaseDestination;

    private LiquidateAssetsInputBoundary liquidateAssets;

    private EndTurnInputBoundary endTurn;

    /**
     * Construct the TurnController object. Before use, the {@link #setInputBoundaries(BuildBuildingInputBoundary, MortgagePropertyInputBoundary, MovePlayerInputBoundary, TradeInputBoundary, TryToGetOutOfJailInputBoundary, ViewInventoryInputBoundary, LiquidateAssetsInputBoundary, EndTurnInputBoundary)}
     * method must be called to specify the input boundaries.
     */
    public TurnController(GameState gameState) {
        this.buildBuilding = null;
        this.mortgageProperty = null;
        this.movePlayer = null;
        this.trade = null;
        this.getOutOfJail = null;
        this.viewInventory = null;
        this.gameState = gameState;
        this.nextEndUseCaseDestination = EndUseCaseDestination.DEFAULT_DESTINATION;
    }

    /**
     * Set the input boundaries to use in this controller. Must be called before other methods are called.
     */
    public void setInputBoundaries(BuildBuildingInputBoundary buildBuilding,
                                   MortgagePropertyInputBoundary mortgageProperty,
                                   MovePlayerInputBoundary movePlayer,
                                   TradeInputBoundary trade,
                                   TryToGetOutOfJailInputBoundary getOutOfJail,
                                   ViewInventoryInputBoundary viewInventory,
                                   LiquidateAssetsInputBoundary liquidateAssets,
                                   EndTurnInputBoundary endTurn) {
        this.buildBuilding = buildBuilding;
        this.mortgageProperty = mortgageProperty;
        this.movePlayer = movePlayer;
        this.trade = trade;
        this.getOutOfJail = getOutOfJail;
        this.viewInventory = viewInventory;
        this.liquidateAssets = liquidateAssets;
        this.endTurn = endTurn;

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
        movePlayer.startAction(player, true);
    }

    public void endRollDice(Player player, boolean rollAgain) {
        if(!rollAgain) {
            gameState.playerRolledToMove();
        }
        endUseCase();
    }

    public void buyProperty(Player player, Property property) {
        player.addProperty(property);
        player.subtractMoney(property.getPurchasePrice());
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

    /* Mortgage property related methods */
//    public void showMortgageableProperties(Player player) { mortgageProperty.showMortgageOptions(player); }

    public void mortgageProperty(Player player, Property property) {
        mortgageProperty.mortgage(player, property);
    }

//    public void showUnmortgageableProperties(Player palyer) { mortgageProperty.showUnmortgageOptions(player); }

    public void unmortgageProperty(Player player, Property property) {
        mortgageProperty.unmortgage(player, property);
    }

    /* BuildBuilding Related Methods */
//    public void getBuildableProperties(Player player) { buildBuilding.showBuildingOptions(player); }

    public void buildHouse(Player player, ColorPropertyTile property) {
        buildBuilding.buildHouse(player, property);
    }

    public void buildHotel(Player player, ColorPropertyTile property) {
        buildBuilding.buildHotel(player, property);
    }

//    public void getBuiltOnProperties(Player player) { buildBuilding.showSellOptions(player); }

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

    public void makeOffer(Player offering, Player receiving, TradeOffer player1Offer) {
//        TradeOffer player1Offer = new TradeOffer(player1Money - player2Money, player1JailCard - player2JailCard,
//                (ArrayList<Property>) player1Property, (ArrayList<Property>) player2Property, offering, receiving);
        trade.makeOffer(player1Offer, offering, receiving);
    }

    public void acceptTradeOffer(Player offering, Player receiving, TradeOffer tradeOffer) {
        trade.getResultOfTradeOffer(1, offering, receiving, tradeOffer);
    }

    public void declineTradeOffer(Player offering, Player receiving, TradeOffer tradeOffer) {
        trade.getResultOfTradeOffer(3, offering, receiving, tradeOffer);
    }

    public void counterOffer(Player offering, Player receiving, TradeOffer tradeOffer) {
        // The receiving player is now making the offer
        trade.getResultOfTradeOffer(2, offering, receiving, tradeOffer);
    }

    /* LiquidityUseCase related methods*/
    public void getPlayerOptions(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    public void getMortgageableProperties(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    public void getPropertiesWithHouses(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    public void bankruptcy(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    /* ViewInventory Related Methods */
    public void showInventory(Player currentPlayer, List<Player> playerList) {
        viewInventory.displayInfo(currentPlayer, playerList);
    }

    public void endTurn(Player player){
        endTurn.endTurn(player);
    }

    enum EndUseCaseDestination {
        DEFAULT_DESTINATION,
        LIQUIDATE_ASSETS
    }
}
