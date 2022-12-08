package game_entities.tiles;
import game_entities.Board;
import game_entities.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
public class TestUtilityTile {
    int[] rent = {20, 40};
    UtilityTile utilityTile = new UtilityTile("UtilityTile", "Utility Tile", 60,
            rent, 10, 20);
    ArrayList<Tile> test = new ArrayList<Tile>(){{
        add(utilityTile);
    }};
    Board board = new Board(test);
    Player player1 = new Player("Test", "test", 1500, board);

    @Test
    public void NumUtilityOwned() {
        utilityTile.isOwned();
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(utilityTile);
        int test = utilityTile.numUtilityOwned(propertyList);
        assertEquals(test, 0);
    }
    @Test
    public void getRent() {
        // Test if the correct rent price is obtained when the getRent method is called
        Player player1 = new Player("Test", "test", 1500, board);
        player1.setLastRoll(3, 2);
        List<Property> propertyList = board.getPropertyTiles();
        int test = utilityTile.getRent(player1, propertyList);
        assertEquals(test, 100);
    }
    @Test
    public void action() {
        utilityTile.isOwned();
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = utilityTile.action(player1, board);
        assertEquals(test.getFlavorText(), "Would you Like to Purchase " + utilityTile.getTileDisplayName()
                + " for" + utilityTile.getPurchasePrice() + " ?");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), player1.getPosition());
    }
}