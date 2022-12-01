package GameEntities.Tiles;
import GameEntities.Board;
import GameEntities.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
public class TestTaxTile {
    TaxTile taxTile = new TaxTile("TaxTile", "Tax Tile", 100 );
    ArrayList<Tile> test = new ArrayList<>(){{
        add(taxTile);
    }};


    Board board = new Board(test);
    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = taxTile.action(player1, board);
        assertEquals(test.getFlavorText(), "You paid $" + 100 + " Tax Tile" + " !");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), board.getTilePosition("TaxTile"));

    }
}
