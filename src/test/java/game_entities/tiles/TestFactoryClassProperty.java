package game_entities.tiles;

import static org.junit.Assert.*;

import game_entities.tiles.*;


import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class TestFactoryClassProperty {
    @Test
    public void testExtractorColorProperty() throws FileNotFoundException {
        //test that the extractor class works for color property csv file
        List<List<String>> colorProperty = FactoryProperty.extractor
                ("src/main/resources/Data/property_csvs/Color Properties Monopoly.csv");
        assertEquals(colorProperty.get(0).get(0), "Property Color");
        assertEquals(colorProperty.get(0).get(4), "Rent(Set Owned)");
        assertEquals(colorProperty.get(3).get(0), "Light Blue");
        assertEquals(colorProperty.get(3).get(2), "100");
        assertEquals(colorProperty.get(22).get(1), "Boardwalk");
        assertEquals(colorProperty.get(10).get(2), "180");
        assertEquals(colorProperty.get(10).get(8), "750");
    }
    @Test
    public void testExtractorRailRoad() throws  FileNotFoundException {
        //test that the extractor class works for RailRoad property csv file
        List<List<String>> railRoadProperty = FactoryProperty.extractor
                ("src/main/resources/Data/property_csvs/Station Properties Monopoly.csv");
        assertEquals(railRoadProperty.get(0).get(0), "Name");
        assertEquals(railRoadProperty.get(1).get(0), "Reading Railroad");
        assertEquals(railRoadProperty.get(1).get(2), "25");
        assertEquals(railRoadProperty.get(4).get(0), "Short Line");
        assertEquals(railRoadProperty.get(4).get(8), "shortline");
    }
    @Test
    public void testExtractorUtility() throws FileNotFoundException {
        //tests that the extractor class works for utility property csv file
        List<List<String>> utilityTile = FactoryProperty.extractor
                ("src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv");
        assertEquals(utilityTile.get(0).get(0), "Name");
        assertEquals(utilityTile.get(1).get(0), "Electric Company");
        assertEquals(utilityTile.get(2).get(0), "Water Works");
        assertEquals(utilityTile.get(1).get(1), "150");
        assertEquals(utilityTile.get(2).get(1), "150");
        assertEquals(utilityTile.get(1).get(6), "electric");
        assertEquals(utilityTile.get(2).get(6), "water");
    }
    @Test
    public void testInitializeColorProperties() throws FileNotFoundException {
        //test if the initializeColorProperties works as expected
        List<ColorPropertyTile> propertyList = FactoryProperty.initializeColorProperties
                ("src/main/resources/Data/property_csvs/Color Properties Monopoly.csv");
        assertEquals(propertyList.get(0).getTileName(), "Mediterranean Avenue");
        assertEquals(propertyList.get(21).getTileName(), "Boardwalk");
    }
    @Test
    public void testInitializeRailRoadProperties() throws FileNotFoundException {
        //test if the initializeRailRoadProperties works as expected
        List<RailroadTile> propertyList = FactoryProperty.initializeRailRoadProperties
                ("src/main/resources/Data/property_csvs/Station Properties Monopoly.csv");
        assertEquals(propertyList.get(0).getTileName(), "Reading Railroad");
        assertEquals(propertyList.get(3).getTileName(), "Short Line");
    }
    @Test
    public void testInitializeUtilityProperties() throws FileNotFoundException {
        //test if the initializeUtilityProprieties works as expected
        List<UtilityTile> propertyList = FactoryProperty.initializeUtilityProperties
                ("src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv");
        assertEquals(propertyList.get(0).getTileName(), "Electric Company");
        assertEquals(propertyList.get(1).getTileName(), "Water Works");
    }
}