package game;

import game_entities.Board;
import game_entities.FactoryBoard;
import game_entities.Player;
import turn_interface_adapters.LiquidateAssetsInterfaceAdapter;
import turn_interface_adapters.TurnController;
import turn_use_cases.end_turn_use_case.EndTurnInputBoundary;
import turn_use_cases.end_turn_use_case.EndTurnOutputBoundary;
import turn_use_cases.end_turn_use_case.EndTurnPresenter;
import turn_use_cases.end_turn_use_case.EndTurnUseCase;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsInputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsOutputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsUseCase;
import turn_use_cases.move_player_use_case.MovePlayerInputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerOutputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerPresenter;
import turn_use_cases.move_player_use_case.MovePlayerUseCase;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailInputBoundary;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailOutputBoundary;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailPresenter;
import turn_use_cases.try_to_get_out_of_jail_use_case.TryToGetOutOfJailUseCase;
import turn_use_cases.view_inventory.ViewInventory;
import turn_use_cases.view_inventory.ViewInventoryInputBoundary;
import turn_use_cases.view_inventory.ViewInventoryOutputBoundary;
import turn_use_cases.view_inventory.ViewInventoryPresenter;
import ui.GameView;

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
    public Game() {
        String PROPERTY_CSV = "src/main/resources/Data/property_csvs/Color Properties Monopoly.csv";
        String RR_CSV = "src/main/resources/Data/property_csvs/Station Properties Monopoly.csv";
        String UTILITY_CSV = "src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv";
        String CARDS_CSV = "src/main/resources/cards.csv";
        String SAVES_DIRECTORY = "./saves";
        gameView = new GameView();
        this.players = new ArrayList<>();
        constructDefaultGame(PROPERTY_CSV, RR_CSV, UTILITY_CSV, CARDS_CSV, SAVES_DIRECTORY, gameView);
    }

    /**
     * Starts the game and shows the main window.
     */
    public void startGame() {
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

    public void addPlayer(Player player) {
        players.add(player);
    }

    public GameView getGameView() {
        return gameView;
    }

    private void constructDefaultGame(String property_csv, String rr_csv, String utility_csv, String cards_csv, String saves_directory, GameView gameView) {
        try {
            board = FactoryBoard.boardMaker(property_csv, utility_csv, rr_csv, cards_csv);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        JPanel actionDialogBoxes = gameView.getActionDialogBoxes();
        JPanel autosaveInfo = gameView.getAutosaveInfo();
        JLayeredPane boardLayeredPane = gameView.getBoardLayeredPane();

        //Currently constructs default players for testing
        Player player1 = new Player("player1", "battleship", 1500, board);
        Player player2 = new Player("player2", "car", 1500, board);
        Player player3 = new Player("player3", "thimble", 1500, board);
        Player player4 = new Player("player4", "hat", 1500, board);

        addPlayer(player1);
        addPlayer(player2);
        addPlayer(player3);
        addPlayer(player4);

        SaveGameState save = new SaveGameStateSerialize(saves_directory);
        LoadGameState load = new LoadGameStateSerialize(saves_directory);

        turnController = new TurnController();
        GameStatePresenter gameStatePresenter = new GameStatePresenter(actionDialogBoxes, turnController, autosaveInfo);
        gameState = new GameState(players, "gameName1", save, gameStatePresenter);
        GameStateOutputBoundary presenter = new GameStatePresenter(actionDialogBoxes, turnController, autosaveInfo);
        gameState.setPresenter(presenter);

        constructUseCases(turnController, gameState, board, boardLayeredPane, actionDialogBoxes);
    }

    private void constructUseCases(TurnController turnController, GameState gameState, Board board, JLayeredPane jLayeredPane, JPanel actionDialogBoxes) {
        EndTurnOutputBoundary endTurnPresenter = new EndTurnPresenter(actionDialogBoxes, turnController);
        EndTurnInputBoundary endTurn = new EndTurnUseCase(endTurnPresenter, gameState);

        MovePlayerOutputBoundary movePlayerPresenter = new MovePlayerPresenter(jLayeredPane, actionDialogBoxes, 9.0 / 15, gameState.getAllPlayers(), turnController, "src/main/resources/TilePositions.txt");
        MovePlayerInputBoundary movePlayer = new MovePlayerUseCase(movePlayerPresenter, board, endTurn);

        TryToGetOutOfJailOutputBoundary leaveJailPresenter = new TryToGetOutOfJailPresenter(actionDialogBoxes, turnController);
        TryToGetOutOfJailInputBoundary leaveJail = new TryToGetOutOfJailUseCase(leaveJailPresenter, board, endTurn, movePlayer, movePlayerPresenter);

        ViewInventoryOutputBoundary viewInventoryPresenter = new ViewInventoryPresenter();
        ViewInventoryInputBoundary viewInventory = new ViewInventory(viewInventoryPresenter);

        LiquidateAssetsOutputBoundary liquidateAssetsPresenter = new LiquidateAssetsInterfaceAdapter(turnController, actionDialogBoxes, (CardLayout) actionDialogBoxes.getLayout());
        LiquidateAssetsInputBoundary liquidateAssets = new LiquidateAssetsUseCase(liquidateAssetsPresenter);

        turnController.initializeAttributes(gameState, null, null, movePlayer, null, leaveJail, viewInventory, liquidateAssets, endTurn);
    }
}
