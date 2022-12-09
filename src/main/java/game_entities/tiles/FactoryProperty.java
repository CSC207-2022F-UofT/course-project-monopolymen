package game_entities.tiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryProperty {
    /**
     * Extract and put each property into an ArrayList
     *
     * @param path                          the path of the file that we are trying to extract information from
     * @return returns an ArrayList with all the information needed
     */
    public static List<List<String>> extractor(String path) {
        List<List<String>> properties = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                properties.add(Arrays.asList(split));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * Take a CSV file with the information of Colored Properties and create and return ColorPropertyTile objects.
     *
     * @param path                          the CSV location of the properties
     * @return                              return an ArrayList which includes all the Color Property tiles
     */
    public static List<ColorPropertyTile> initializeColorProperties(String path) {
        List<List<String>> colorProperties = extractor(path);
        List<ColorPropertyTile> returnProperties= new ArrayList<>();
        colorProperties.remove(0);
        for(List<String> line : colorProperties){
            String color = line.get(0);
            String displayName = line.get(1);
            int price = Integer.parseInt(line.get(2));
            int rent = Integer.parseInt(line.get(3));
            int rentSet = Integer.parseInt(line.get(4));
            int rentHouse1 = Integer.parseInt(line.get(5));
            int rentHouse2 = Integer.parseInt(line.get(6));
            int rentHouse3 = Integer.parseInt(line.get(7));
            int rentHouse4 = Integer.parseInt(line.get(8));
            int rentHotel = Integer.parseInt(line.get(9));
            int buildingCost = Integer.parseInt(line.get(10));
            int mortgage = Integer.parseInt(line.get(11));
            int unMortgage = Integer.parseInt(line.get(12));
            String useName = line.get(13);
            int[] rentArr = {rent, rentSet, rentHouse1, rentHouse2, rentHouse3, rentHouse4, rentHotel};
            ColorPropertyTile add = new ColorPropertyTile(color, useName, displayName, price, rentArr, buildingCost,
                    mortgage, unMortgage);
            returnProperties.add(add);
        }
        return returnProperties;
    }

    /**
     * Take a CSV file with the information of RailRoad Properties and create and return RailroadTile objects.
     *
     * @param path                          the CSV location of the properties
     * @return                              return an ArrayList which includes all the Color Property tiles
     */
    public static List<RailroadTile> initializeRailRoadProperties(String path) {
        List<List<String>> railRoad = extractor(path);
        List<RailroadTile> returnProperties= new ArrayList<>();
        railRoad.remove(0);
        for(List<String> line : railRoad){
            String displayName = line.get(0);
            int price = Integer.parseInt(line.get(1));
            int rentOwn1 = Integer.parseInt(line.get(2));
            int rentOwn2 = Integer.parseInt(line.get(3));
            int rentOwn3 = Integer.parseInt(line.get(4));
            int rentOwn4 = Integer.parseInt(line.get(5));
            int mortgage = Integer.parseInt(line.get(6));
            int unMortgage = Integer.parseInt(line.get(7));
            String useName = line.get(8);
            int[] rentArr = {rentOwn1, rentOwn2, rentOwn3, rentOwn4};
            RailroadTile add = new RailroadTile(useName, displayName, price, rentArr, mortgage, unMortgage);
            returnProperties.add(add);
        }
        return returnProperties;
    }

    /**
     * Take a CSV file with the information of UtilityTile Properties and create and return UtilityTile objects.
     *
     * @param path                          the CSV location of the properties
     * @return                              return an ArrayList which includes all the Color Property tiles
     */
    public static List<UtilityTile> initializeUtilityProperties(String path) {
        List<List<String>> utilityProperties = extractor(path);
        List<UtilityTile> returnProperties= new ArrayList<>();
        utilityProperties.remove(0);
        for(List<String> line : utilityProperties){
            String displayName = line.get(0);
            int price = Integer.parseInt(line.get(1));
            int rent1 = Integer.parseInt(line.get(2));
            int rent2 = Integer.parseInt(line.get(3));
            int mortgage = Integer.parseInt(line.get(4));
            int unMortgage = Integer.parseInt(line.get(5));
            String useName = line.get(6);
            int[] rentArr = {rent1, rent2};
            UtilityTile add = new UtilityTile(useName, displayName, price, rentArr, mortgage, unMortgage);
            returnProperties.add(add);
        }
        return returnProperties;
    }
}
