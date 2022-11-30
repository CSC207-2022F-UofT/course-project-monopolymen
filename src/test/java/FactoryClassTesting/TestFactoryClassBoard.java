package FactoryClassTesting;
import static org.junit.Assert.*;

import GameEntities.Board;
import GameEntities.Cards.*;
import GameEntities.FactoryBoard;
import GameEntities.Tiles.*;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class TestFactoryClassBoard {
    @Test
    public void testOrder() throws FileNotFoundException {
        List<ColorPropertyTile> colorProperties = FactoryProperty.initializeColorProperties
                ("src/main/resources/Data/property_csvs/Color Properties Monopoly.csv");
        List<RailroadTile> railroadProperties = FactoryProperty.initializeRailRoadProperties
                ("src/main/resources/Data/property_csvs/Station Properties Monopoly.csv");
        List<UtilityTile> utilityProperties = FactoryProperty.initializeUtilityProperties
                ("src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv");
        ArrayList<Tile> test = FactoryBoard.order(colorProperties, railroadProperties, utilityProperties);
        assertEquals(test.size(), 40);
        assertEquals(test.get(0).getTileName(), "GoTile");
        assertEquals(test.get(39).getTileName(), "Boardwalk");
    }
    @Test
    public void testBoardMaker() throws FileNotFoundException {
        Board testBoard = FactoryBoard.boardMaker
                ("src/main/resources/Data/property_csvs/Color Properties Monopoly.csv",
                        "src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv",
                        "src/main/resources/Data/property_csvs/Station Properties Monopoly.csv",
                        "src/main/resources/Cards.csv");
        assertEquals(testBoard.getTilesList().size(), 40);
        assertEquals(testBoard.getJailTilePosition(), 10);
    }
}

