package game_entities.tiles;
import game_entities.Board;
import game_entities.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
public class TestColorPropertyTile {
    int[] rent = {4, 8, 20, 30, 50, 80, 100};
    ColorPropertyTile colorPropertyTile = new ColorPropertyTile("Brown", "ArcticAvenue",
           "Arctic Avenue", 60, rent, 50, 60, 10);
    ArrayList<Tile> test = new ArrayList<Tile>(){{
        add(colorPropertyTile);
    }};
    Board board = new Board(test);
    @Test
    public void checkSetOwned() {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(colorPropertyTile);
        boolean test = colorPropertyTile.checkSetOwned(propertyList);
        assertEquals(test, false);
    }
    @Test
    public void getRent() {
        // Test if the correct rent price is obtained when the getRent method is called
        Player player1 = new Player("Test", "test", 1500, board);
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(colorPropertyTile);
        int test = colorPropertyTile.getRent(player1, propertyList);
        assertEquals(test, -1);
    }
    @Test
    public void addHouse() {
        // Test if houses are added
        colorPropertyTile.addHouse(1);
        assertEquals(colorPropertyTile.getNumHouses(), 1);

    }
    @Test
    public void subtractHouse1() {
        // Test if houses are subtracted
        colorPropertyTile.addHouse(4);
        colorPropertyTile.subtractHouse(1);
        assertEquals(colorPropertyTile.getNumHouses(), 3);

    }
    @Test
    public void subtractHouse2() {
        // Test if number of houses remain 0 when subtracted to a negative number
        colorPropertyTile.subtractHouse(2);
        assertEquals(colorPropertyTile.getNumHouses(), 0);

    }
    @Test
    public void action() {
        colorPropertyTile.isOwned();
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = colorPropertyTile.action(player1, board);
        assertEquals(test.getFlavorText(), "Would you Like to Purchase " + colorPropertyTile.getTileDisplayName()
                + " for" + colorPropertyTile.getPurchasePrice() + " ?");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), player1.getPosition());
    }


}
