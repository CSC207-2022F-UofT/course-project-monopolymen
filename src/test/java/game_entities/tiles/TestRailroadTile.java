package game_entities.tiles;

import game_entities.Board;
import game_entities.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class TestRailroadTile {
    int[] rent = {20, 40};
    RailroadTile railRoadTile = new RailroadTile("RailRoad", "Rail Road", 60,
            rent, 10, 20);
    ArrayList<Tile> test = new ArrayList<>() {{
        add(railRoadTile);
    }};
    Board board = new Board(test);

    @Test
    public void NumRailroadOwned() {
        railRoadTile.isOwned();
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(railRoadTile);
        int test = railRoadTile.numRailroadOwned(propertyList);
        assertEquals(test, 0);
    }
    @Test
    public void getRent() {
        // Test if the correct rent price is obtained when the getRent method is called
        Player player1 = new Player("Test", "test", 1500, board);
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(railRoadTile);
        int test = railRoadTile.getRent(player1, propertyList);
        assertEquals(test, -1);
    }
    @Test
    public void action() {
        railRoadTile.isOwned();
        Player player1 = new Player("Test", "test", 1500, board);
        TileActionResultModel test = railRoadTile.action(player1, board);
        assertEquals(test.getFlavorText(), "Would you Like to Purchase " + railRoadTile.getTileDisplayName()
                + " for" + railRoadTile.getPurchasePrice() + " ?");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), player1.getPosition());
    }

}
