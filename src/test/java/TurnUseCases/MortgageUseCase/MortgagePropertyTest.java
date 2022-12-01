package TurnUseCases.MortgageUseCase;

import GameEntities.Board;
import GameEntities.Player;
import GameEntities.Tiles.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class MortgagePropertyTest {

    ArrayList<Tile> tiles = new ArrayList<Tile>();
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
    public void setUp() throws Exception {
        property_2.addHouse(1);
        player_1.addProperty(property_1);
        player_1.addProperty(water);
        player_2.addProperty(property_2);
        player_2.addProperty(railroad);
    }

    @After
    public void tearDown() throws Exception {
        property_2.subtractHouse(1);
        player_1.sellProperty(property_1);
        player_1.sellProperty(water);
        player_2.sellProperty(property_2);
        player_2.sellProperty(railroad);
    }

    @Test
    public void testMortgagePropertyWithoutBuildings_1(){
        mortgageProperty.mortgage(player_1, property_1);
        assertEquals(player_1.getMoney(), 1650);
        assertEquals(property_1.isMortgaged(), true);
    }

    @Test
    public void testMortgagePropertyWithoutBuildings_2(){
        mortgageProperty.mortgage(player_1, water);
        assertEquals(player_1.getMoney(), 1600);
        assertEquals(water.isMortgaged(), true);
    }

    @Test
    public void testMortgagePropertyWithoutBuildings_3(){
        mortgageProperty.mortgage(player_2, railroad);
        assertEquals(player_2.getMoney(), 1650);
        assertEquals(railroad.isMortgaged(), true);
    }

    @Test
    public void testMortgagePropertyWithBuildings(){
        mortgageProperty.mortgage(player_2, property_2);
        assertEquals(player_2.getMoney(), 1500);
        assertEquals(property_2.isMortgaged(), false);
    }

    @Test
    public void testUnmortgagePropertyWithoutBuildings_1(){
        mortgageProperty.mortgage(player_1, property_1);
        mortgageProperty.unmortgage(player_1, property_1);
        assertEquals(player_1.getMoney(), 1485);
        assertEquals(property_1.isMortgaged(), false);
    }

    @Test
    public void testUnmortgagePropertyWithoutBuildings_2(){
        mortgageProperty.mortgage(player_1, water);
        mortgageProperty.unmortgage(player_1, water);
        assertEquals(player_1.getMoney(), 1490);
        assertEquals(water.isMortgaged(), false);
    }

    @Test
    public void testUnmortgagePropertyWithoutBuildings_3(){
        mortgageProperty.mortgage(player_2, railroad);
        mortgageProperty.unmortgage(player_2, railroad);
        assertEquals(player_2.getMoney(), 1485);
        assertEquals(railroad.isMortgaged(), false);
    }

}
