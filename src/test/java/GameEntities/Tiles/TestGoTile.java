package GameEntities.Tiles;

import GameEntities.Board;
import GameEntities.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestGoTile {
    GoTile goTile = new GoTile();
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);


    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = goTile.action(player1, board);
        assertEquals(test.getFlavorText(), "You landed on Go!");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), 0);

    }

    @Test
    public void passing() {
        Player player1 = new Player("Test", "test", 1500, board);
        TilePassResultModel test = goTile.passing(player1);
        TilePassResultModel passGo = new TilePassResultModel(true, "You got $200!");

        assertEquals(test.isActionTaken(), passGo.isActionTaken());
        assertEquals(test.getFlavorText(), passGo.getFlavorText());
        assertEquals(1700, player1.getMoney());


    }
}
