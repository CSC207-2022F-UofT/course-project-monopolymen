package turn_use_cases.mortgage_use_case;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class MortgagePropertyTest {

    ArrayList<Tile> tiles = new ArrayList<>();
    Board board = new Board(tiles);
    int[] rentPrice = new int[]{0};

    Player player_1 = new Player("player1", "player1", 1500, board);

    Player player_2 = new Player("player1", "player1", 1500, board);

    ColorPropertyTile property_1 = new ColorPropertyTile("red", "property_1",
            "Property 1", 0, rentPrice, 0, 150, 165);

    ColorPropertyTile property_2 = new ColorPropertyTile("red", "property_1",
            "Property 1", 0, rentPrice, 0, 100, 110);

    UtilityTile water = new UtilityTile("water", "Water",
            150, rentPrice, 100, 110);

    RailroadTile railroad = new RailroadTile("railroad", "Railroad",
            200, rentPrice, 150,165);

    MortgagePropertyPresenter presenter = new MortgagePropertyPresenter();

    MortgageProperty mortgageProperty = new MortgageProperty(presenter);


    @Before
    public void setUp() {
        property_2.addHouse(1);
        player_1.addProperty(property_1);
        player_1.addProperty(water);
        player_2.addProperty(property_2);
        player_2.addProperty(railroad);
    }

    @After
    public void tearDown() {
        property_2.subtractHouse(1);
        player_1.sellProperty(property_1);
        player_1.sellProperty(water);
        player_2.sellProperty(property_2);
        player_2.sellProperty(railroad);
    }

    @Test
    public void testMortgagePropertyWithoutBuildings_3(){
        mortgageProperty.mortgage(player_2, railroad);
        assertEquals(player_2.getMoney(), 1650);
        assertTrue(railroad.isMortgaged());
    }

    @Test
    public void testMortgagePropertyWithBuildings(){
        mortgageProperty.mortgage(player_2, property_2);
        assertEquals(player_2.getMoney(), 1500);
        assertFalse(property_2.isMortgaged());
    }

    @Test
    public void testUnmortgagePropertyWithoutBuildings_1(){
        mortgageProperty.mortgage(player_1, property_1);
        mortgageProperty.unmortgage(player_1, property_1);
        assertEquals(player_1.getMoney(), 1485);
        assertFalse(property_1.isMortgaged());
    }

    @Test
    public void testUnmortgagePropertyWithoutBuildings_2(){
        mortgageProperty.mortgage(player_1, water);
        mortgageProperty.unmortgage(player_1, water);
        assertEquals(player_1.getMoney(), 1490);
        assertFalse(water.isMortgaged());
    }

    @Test
    public void testUnmortgagePropertyWithoutBuildings_3(){
        mortgageProperty.mortgage(player_2, railroad);
        mortgageProperty.unmortgage(player_2, railroad);
        assertEquals(player_2.getMoney(), 1485);
        assertFalse(railroad.isMortgaged());
    }

}
