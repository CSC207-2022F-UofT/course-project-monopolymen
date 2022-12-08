package turn_use_cases.build_building_use_case;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.Tile;
import game_entities.tiles.UtilityTile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import turn_use_cases.build_use_case.BuildBuildings;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BuildBuildingTest {

    int[] rentPrice = new int[]{0};

    ColorPropertyTile property_1 = new ColorPropertyTile("Brown", "property_1",
            "Property 1", 0, rentPrice, 100, 150, 165);

    ColorPropertyTile property_2 = new ColorPropertyTile("Brown", "property_2",
            "Property 1", 0, rentPrice, 200, 100, 110);

    ColorPropertyTile property_3 = new ColorPropertyTile("red", "property_3",
            "Property 1", 0, rentPrice, 150, 100, 110);

    ColorPropertyTile property_4 = new ColorPropertyTile("red", "property_4",
            "Property 1", 0, rentPrice, 150, 100, 110);

    UtilityTile water = new UtilityTile("water", "Water",
            150, rentPrice, 100, 110);

    ArrayList<Tile> tiles = new ArrayList<>(Arrays.asList(property_1, property_2, property_3, property_4, water));

    Board board = new Board(tiles);

    Player player_1 = new Player("player1", "player1", 1500, board);

    BuildBuildingPresenter presenter = new BuildBuildingPresenter();

    BuildBuildings buildBuildings = new BuildBuildings(presenter, board);

    @Before
    public void setUp() throws Exception {
        player_1.addProperty(property_1);
        player_1.addProperty(property_2);
        player_1.addProperty(property_3);
        player_1.addProperty(water);
    }

    @After
    public void tearDown() throws Exception {
        player_1.sellProperty(property_1);
        player_1.sellProperty(property_2);
        player_1.sellProperty(property_3);
        player_1.sellProperty(water);
    }

    @Test
    public void testIsBuildable(){
        System.out.println(board.getPropertyTiles());
        System.out.println(property_4.isOwned());
        boolean a = property_1.checkSetOwned(board.getPropertyTiles());
        System.out.println(a);
    }

    @Test
    public void testIsBuildable_1(){
        boolean flag = buildBuildings.isBuildable(player_1, property_1);
        assertEquals(flag, true);
    }

    @Test
    public void testIsBuildable_2(){
        boolean flag = buildBuildings.isBuildable(player_1, property_3);
        assertEquals(flag, false);
    }

    @Test
    public void testIsBuildable_3(){
        property_1.addHouse(2);
        boolean flag = buildBuildings.isBuildable(player_1, property_1);
        assertEquals(flag, false);
    }

    @Test
    public void testIsBuildable_4(){
        boolean flag = buildBuildings.isBuildable(player_1, property_4);
        assertEquals(flag, false);
    }


    @Test
    public void testBuildBuilding_1(){
        buildBuildings.buildHouse(player_1, property_1);
        int i = property_1.getNumHouses();
        assertEquals(i, 1);
    }

    @Test
    public void testBuildBuilding_2(){
        property_1.addHouse(4);
        property_2.addHouse(4);
        buildBuildings.buildHotel(player_1, property_1);
        int i = property_1.getNumHotels();
        assertEquals(i, 1);
    }

    @Test
    public void testBuildBuilding_3(){
        property_1.addHouse(3);
        property_2.addHouse(4);
        System.out.println(buildBuildings.isBuildable(player_1, property_1));
        buildBuildings.buildHotel(player_1, property_1);
        int i = property_1.getNumHotels();
        assertEquals(i, 0);
    }

    @Test
    public void testIsSellable_1(){
        property_1.addHouse(4);
        property_2.addHouse(4);
        boolean flag = buildBuildings.isSellable(player_1, property_1);
        assertEquals(flag, true);
    }

    @Test
    public void testIsSellable_2(){
        property_1.addHouse(3);
        property_2.addHouse(4);
        boolean flag = buildBuildings.isSellable(player_1, property_1);
        assertEquals(flag, false);
    }

    @Test
    public void testIsSellable_3(){
        boolean flag = buildBuildings.isSellable(player_1, property_1);
        assertEquals(flag, false);
    }

    @Test
    public void testSellBuiding(){
        property_1.addHouse(4);
        property_2.addHouse(4);
        buildBuildings.sellHouse(player_1, property_1);
        int a = property_1.getNumHouses();
        int b = player_1.getMoney();
        assertEquals(a, 3);
        assertEquals(b, 1550);
    }


}
