package game_entities.tiles;
import game_entities.Board;
import game_entities.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
public class TestFreeParkingTile {
    FreeParkingTile freeParkingTile = new FreeParkingTile();
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);

    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = freeParkingTile.action(player1, board);
        assertEquals(test.getFlavorText(), "You land on free parking tile! Nothing happens!");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), player1.getPosition());
    }
}
