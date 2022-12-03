package game;

import game_entities.Board;
import game_entities.FactoryBoard;
import game_entities.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSaveGameStateSerialize {
    protected GameState gameState;
    protected SaveGameStateSerialize save;

    @Rule
    public TemporaryFolder testDirectory = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        String PROPERTY_CSV = "src/main/resources/Data/property_csvs/Color Properties Monopoly.csv";
        String RR_CSV = "src/main/resources/Data/property_csvs/Station Properties Monopoly.csv";
        String UTILITY_CSV = "src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv";
        String CARDS_CSV = "src/main/resources/cards.csv";
        File tempDir = testDirectory.newFolder();
        String SAVES_DIRECTORY = tempDir.getAbsolutePath();
        System.out.println("Testing on path: " + SAVES_DIRECTORY);

        save = new SaveGameStateSerialize(SAVES_DIRECTORY);
        GameStateOutputBoundary presenter = new TestableGameStatePresenter();

        Board board = FactoryBoard.boardMaker(PROPERTY_CSV, UTILITY_CSV, RR_CSV, CARDS_CSV);

        Player player1 = new Player("player1", "icon1", 1000, board);
        Player player2 = new Player("player2", "icon2", 1100, board);
        Player player3 = new Player("player3", "icon3", 1200, board);
        Player player4 = new Player("player4", "icon4", 1300, board);

        player1.addGetOutOfJailCard();
        player2.enterJail();
        player3.addProperty(board.getPropertyTiles().get(0));
        player4.addProperty(board.getPropertyTiles().get(1));
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        gameState = new GameState(players, "gameName1", save, presenter);
    }

    @Test
    public void testSave() {
        String SAVE_NAME = "gameStateSave1";
        save.save(gameState, SAVE_NAME);

        File savedFile = new File(save.getSavesDirectory() + SAVE_NAME + ".ser");
        assertTrue(savedFile.exists());
        assertTrue(savedFile.isFile());

        GameState loadedGame;
        try {
            FileInputStream fileIn = new FileInputStream(savedFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            loadedGame = (GameState) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // These errors should not happen given the above assert statements and
            // that the GameState object is created in this class.
            throw new RuntimeException(e);
        }

        assertEquals(gameState, loadedGame);
    }
}
