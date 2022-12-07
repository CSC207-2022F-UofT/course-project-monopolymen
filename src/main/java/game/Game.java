package game;

import game_entities.Board;
import game_entities.FactoryBoard;
import game_entities.Player;
import turn_interface_adapters.*;
import turn_use_cases.build_use_case.BuildBuildingInputBoundary;
import turn_use_cases.build_use_case.BuildBuildingOutputBoundary;
import turn_use_cases.build_use_case.BuildBuildings;
import turn_use_cases.end_turn_use_case.EndTurnInputBoundary;
import turn_use_cases.end_turn_use_case.EndTurnOutputBoundary;
import turn_use_cases.end_turn_use_case.EndTurnPresenter;
import turn_use_cases.end_turn_use_case.EndTurnUseCase;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsInputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsOutputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsUseCase;
import turn_use_cases.mortgage_use_case.MortgageProperty;
import turn_use_cases.mortgage_use_case.MortgagePropertyInputBoundary;
import turn_use_cases.mortgage_use_case.MortgagePropertyOutputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerInputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerOutputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerPresenter;
import turn_use_cases.move_player_use_case.MovePlayerUseCase;
import turn_use_cases.trade_use_case.TradeInputBoundary;
import turn_use_cases.trade_use_case.TradeOutputBoundary;
import turn_use_cases.trade_use_case.TradeUseCase;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailInputBoundary;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailOutputBoundary;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailPresenter;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailUseCase;
import turn_use_cases.view_inventory.ViewInventory;
import turn_use_cases.view_inventory.ViewInventoryInputBoundary;
import turn_use_cases.view_inventory.ViewInventoryOutputBoundary;
import turn_use_cases.view_inventory.ViewInventoryPresenter;
import ui.GameView;
import ui.GameView.PlayerIcon;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Game is an object tha handles construction and management of all entities related to a single game.
 */
public class Game {
    private Board board;
    private GameState gameState;
    private TurnController turnController;
    private final List<Player> players;
    private final GameView gameView;

    /**
     * Construct the default game
     */
    public Game(String gameName) {
        String PROPERTY_CSV = "src/main/resources/Data/property_csvs/Color Properties Monopoly.csv";
        String RR_CSV = "src/main/resources/Data/property_csvs/Station Properties Monopoly.csv";
        String UTILITY_CSV = "src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv";
        String CARDS_CSV = "src/main/resources/cards.csv";
        String SAVES_DIRECTORY = "./saves";
        gameView = new GameView();
        this.players = new ArrayList<>();
        constructDefaultGame(PROPERTY_CSV, RR_CSV, UTILITY_CSV, CARDS_CSV, SAVES_DIRECTORY, gameView, gameName);
    }

    /**
     * Starts the game and shows the main window.
     */
    public void startGame() {
        constructUseCases(turnController, gameState, board);
        gameState.startGame();
        gameView.getMainWindow().setVisible(true);
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a new Player to the game.
     *
     * @param playerName The player's name. Must be unique between players.
     * @param playerIcon The icon representing the player.
     * @param money      The starting money the player has.
     */
    public void addPlayer(String playerName, PlayerIcon playerIcon, int money) {
        Player player = new Player(playerName, playerIcon.name().toLowerCase(), money, board);
        player.setGameState(gameState);
        player.setTurnController(turnController);
        players.add(player);
    }

    public GameView getGameView() {
        return gameView;
    }

    private void constructDefaultGame(String property_csv, String rr_csv, String utility_csv, String cards_csv, String saves_directory, GameView gameView, String gameName) {
        try {
            board = FactoryBoard.boardMaker(property_csv, utility_csv, rr_csv, cards_csv);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        SaveGameState save = new SaveGameStateSerialize(saves_directory);
        LoadGameState load = new LoadGameStateSerialize(saves_directory);

        turnController = new TurnController();
        GameStatePresenter gameStatePresenter = new GameStatePresenter(gameView.getActionDialogBoxes(), turnController, gameView.getAutosaveInfo());
        gameState = new GameState(players, gameName, save, gameStatePresenter);
        GameStateOutputBoundary presenter = new GameStatePresenter(gameView.getActionDialogBoxes(), turnController, gameView.getAutosaveInfo());
        gameState.setPresenter(presenter);
    }

    private void constructUseCases(TurnController turnController, GameState gameState, Board board) {
        JPanel actionDialogBoxes = gameView.getActionDialogBoxes();
        JPanel inventorySummaryBox = gameView.getInventorySummaryBox();
        JLayeredPane boardLayeredPane = gameView.getBoardLayeredPane();
        JFrame mainWindow = gameView.getMainWindow();

        EndTurnOutputBoundary endTurnPresenter = new EndTurnPresenter(actionDialogBoxes, turnController);
        EndTurnInputBoundary endTurn = new EndTurnUseCase(endTurnPresenter, gameState);

        MovePlayerOutputBoundary movePlayerPresenter = new MovePlayerPresenter(mainWindow, boardLayeredPane, actionDialogBoxes, gameState.getAllPlayers(), turnController, "src/main/resources/TilePositions.txt");
        MovePlayerInputBoundary movePlayer = new MovePlayerUseCase(movePlayerPresenter, board, endTurn);

        TryToGetOutOfJailOutputBoundary leaveJailPresenter = new TryToGetOutOfJailPresenter(actionDialogBoxes, turnController);
        TryToGetOutOfJailInputBoundary leaveJail = new TryToGetOutOfJailUseCase(leaveJailPresenter, board, endTurn, movePlayer, movePlayerPresenter);

        ViewInventoryOutputBoundary viewInventoryPresenter = new ViewInventoryPresenter(inventorySummaryBox, gameState.getAllPlayers(), turnController);
        ViewInventoryInputBoundary viewInventory = new ViewInventory(viewInventoryPresenter);

        LiquidateAssetsOutputBoundary liquidateAssetsPresenter = new LiquidateAssetsInterfaceAdapter(turnController, actionDialogBoxes, (CardLayout) actionDialogBoxes.getLayout());
        LiquidateAssetsInputBoundary liquidateAssets = new LiquidateAssetsUseCase(liquidateAssetsPresenter);

        TradeOutputBoundary tradePresenter = new TradePresenter(actionDialogBoxes,(CardLayout) actionDialogBoxes.getLayout(), turnController);
        TradeInputBoundary trade = new TradeUseCase(tradePresenter);

        turnController.initializeAttributes(gameState, null, null, movePlayer, trade, leaveJail, viewInventory, liquidateAssets, endTurn);

        MortgagePropertyOutputBoundary mortgagePropertyPresenter = new MortgagePropertyPresenter(actionDialogBoxes, (CardLayout) actionDialogBoxes.getLayout(), turnController);
        MortgagePropertyInputBoundary mortgage = new MortgageProperty(mortgagePropertyPresenter);

        BuildBuildingOutputBoundary buildBuildingPresenter = new BuildBuildingPresenter(actionDialogBoxes, (CardLayout) actionDialogBoxes.getLayout(), turnController);
        BuildBuildingInputBoundary buildBuilding = new BuildBuildings(buildBuildingPresenter, board);
    }
}