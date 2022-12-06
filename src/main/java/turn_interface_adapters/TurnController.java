package turn_interface_adapters;

import game.GameState;
import game_entities.Board;
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
    private LiquidateAssetsInputBoundary liquidateAssets;
    private EndTurnInputBoundary endTurn;
    private EndUseCaseDestination nextEndUseCaseDestination;

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
        this.liquidateAssets = null;
        this.endTurn = null;
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
        endTurn.endTurn(gameState.currentPlayer());
    }

    public void forceEndTurn() {
        endTurn.forceEndTurn(gameState.currentPlayer());
    }

    /* Move Player/RollDice use case related methods */
    public void rollDice() {
        movePlayer.startAction(gameState.currentPlayer(), true);
    }

    public void endRollDice(boolean rollAgain) {
        if (!rollAgain) {
            gameState.playerRolledToMove();
        }
        endUseCase();
    }

    public void buyProperty(Property property) {
        gameState.currentPlayer().addProperty(property);
        gameState.currentPlayer().subtractMoney(property.getPurchasePrice());
    }

    /* TryToGetOutOfJail use case related methods */
    public void attemptLeaveJail() {
        // Show the player their options for getting out of jail
        getOutOfJail.getPlayerOptions(gameState.currentPlayer());
    }

    public void leaveJailWithChoice(String playerChoice) {
        // Leave jail with the appropriate option.
        getOutOfJail.startAction(playerChoice, gameState.currentPlayer());
    }

    /* Mortgage property related methods */
//    public void showMortgageableProperties() { mortgageProperty.showMortgageOptions(gameState.currentPlayer()); }

    public void mortgageProperty(Property property) {
        mortgageProperty.mortgage(gameState.currentPlayer(), property);
    }

//    public void showUnmortgageableProperties() { mortgageProperty.showUnmortgageOptions(gameState.currentPlayer()); }

    public void unmortgageProperty(Property property) {
        mortgageProperty.unmortgage(gameState.currentPlayer(), property);
    }

    /* BuildBuilding Related Methods */
//    public void getBuildableProperties() { buildBuilding.showBuildingOptions(gameState.currentPlayer()); }

    public void buildHouse(ColorPropertyTile property) {
        buildBuilding.buildHouse(gameState.currentPlayer(), property);
    }

    public void buildHotel(ColorPropertyTile property) {
        buildBuilding.buildHotel(gameState.currentPlayer(), property);
    }

//    public void getBuiltOnProperties() { buildBuilding.showSellOptions(gameState.currentPlayer()); }

    public void sellHouse(ColorPropertyTile property) {
        buildBuilding.sellHouse(gameState.currentPlayer(), property);
    }

    public void sellHotel(ColorPropertyTile property) {
        buildBuilding.sellHotel(gameState.currentPlayer(), property);
    }

    /* Trade Related Methods */
    public void showTradablePlayers() {
        trade.choosePlayer(gameState.getActivePlayers(), gameState.currentPlayer());
    }

    public void startTrade(Player proposing, Player receiving) {
        trade.getTradeOptions(proposing, receiving);
    }

    public void makeOffer(Player offering, Player receiving, TradeOffer player1Offer) {
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

    public void getPlayerOptions(Player affectedPlayer, Player owedPlayer, int owedMoney, GameState gameState, Board board) {
        LiquiditySituation situation = new LiquiditySituation(affectedPlayer, owedPlayer, owedMoney, gameState, board);
        liquidateAssets.getPlayerOptions(situation);
    }

    public void getMortgageableProperties(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    public void getPropertiesWithHouses(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    public void bankruptcy(LiquiditySituation situation) { liquidateAssets.getPlayerOptions(situation); }

    /* ViewInventory Related Methods */
    public void showInventory(Player player, List<Player> playerList) {
        viewInventory.displayInfo(player, playerList);
    }

    enum EndUseCaseDestination {
        DEFAULT_DESTINATION,
        LIQUIDATE_ASSETS
    }
}
