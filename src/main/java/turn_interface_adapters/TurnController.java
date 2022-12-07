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
    private GameState gameState;
    private BuildBuildingInputBoundary buildBuilding;
    private MortgagePropertyInputBoundary mortgageProperty;
    private MovePlayerInputBoundary movePlayer;
    private TradeInputBoundary trade;
    private TryToGetOutOfJailInputBoundary getOutOfJail;
    private ViewInventoryInputBoundary viewInventory;
    private LiquidateAssetsInputBoundary liquidateAssets;
    private EndTurnInputBoundary endTurn;

    /**
     * Construct the TurnController object. Before use, the {@link #initializeAttributes(GameState, BuildBuildingInputBoundary, MortgagePropertyInputBoundary, MovePlayerInputBoundary, TradeInputBoundary, TryToGetOutOfJailInputBoundary, ViewInventoryInputBoundary, LiquidateAssetsInputBoundary, EndTurnInputBoundary)}
     * method must be called to specify the input boundaries.
     */
    public TurnController() {
        this.buildBuilding = null;
        this.mortgageProperty = null;
        this.movePlayer = null;
        this.trade = null;
        this.getOutOfJail = null;
        this.viewInventory = null;
        this.liquidateAssets = null;
        this.endTurn = null;
        this.gameState = null;
    }

    /**
     * Set the input boundaries to use in this controller. Must be called before other methods are called.
     * The reason this order occurs is that each of the inputBoundaries and gameStates have presenters which need
     * to refer to this turnController.
     */
    public void initializeAttributes(GameState gameState,
                                     BuildBuildingInputBoundary buildBuilding,
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
        this.gameState = gameState;
    }

    /**
     * Signifies the end of a use case. Shows the player the turn actions.
     */
    public void endUseCase() {
        gameState.showTurnActions();
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
    public void showMortgageableProperties() { mortgageProperty.showMortgageOption(gameState.currentPlayer()); }

    public void mortgageProperty(Property property) {
        mortgageProperty.mortgage(gameState.currentPlayer(), property);
    }

    public void showUnmortgageableProperties() { mortgageProperty.showUnmortgageOption(gameState.currentPlayer()); }

    public void unmortgageProperty(Property property) {
        mortgageProperty.unmortgage(gameState.currentPlayer(), property);
    }

    /* BuildBuilding Related Methods */
    public void showBuildableProperties() { buildBuilding.showBuildOption(gameState.currentPlayer()); }

    public void buildHouse(ColorPropertyTile property) {
        buildBuilding.buildHouse(gameState.currentPlayer(), property);
    }

    public void buildHotel(ColorPropertyTile property) {
        buildBuilding.buildHotel(gameState.currentPlayer(), property);
    }

    public void showBuiltOnProperties() { buildBuilding.showSellOption(gameState.currentPlayer()); }

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

    public void getMortgageableProperties(LiquiditySituation situation) {liquidateAssets.getMortgageableProperties(situation);}

    public void getPropertiesWithHouses(LiquiditySituation situation) { liquidateAssets.getPropertiesWithHouses(situation); }

    public void bankruptcy(LiquiditySituation situation) { liquidateAssets.bankruptcy(situation); }

    /* ViewInventory Related Methods */
    public void showInventory(Player player, List<Player> playerList) {
        viewInventory.displayInfo(player, playerList);
    }
}
