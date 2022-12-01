package GameEntities.Tiles;
import GameEntities.Board;
import GameEntities.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
public class TestGoToJailTile {
    GoToJailTile gotojailTile = new GoToJailTile();
    ArrayList<Tile> test = new ArrayList<>();

    Board board = new Board(test);

    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = gotojailTile.action(player1, board);
        assertEquals(test.getFlavorText(), "You are being sent to jail.");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), -1);
    }
}
