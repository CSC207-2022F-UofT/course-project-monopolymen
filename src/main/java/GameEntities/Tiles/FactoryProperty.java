package GameEntities.Tiles;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryProperty {
    /**
     * Extract and put each property into an ArrayList
     *
     * @param path                          the path of the file that we are trying to extract information from
     * @return                              returns an ArrayList with all the information needed
     * @throws FileNotFoundException        if the file that we are given a url to is not found, this
     *                                      exception is thrown
     */
    public static List<List<String>> extractor(String path) throws FileNotFoundException{
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
     * @return                              return an ArrayList which includes all the Color Property Tiles
     * @throws FileNotFoundException        if the given file is not found throw this exception
     */
    public static List<ColorPropertyTile> initializeColorProperties(String path) throws FileNotFoundException {
        List<List<String>> colorProperties = extractor(path);
        List<ColorPropertyTile> returnProperties= new ArrayList<ColorPropertyTile>();
        for(List<String> line : colorProperties){
            String color = line.get(1);
            String name = line.get(2);
            int price = Integer.parseInt(line.get(3));
            int rent = Integer.parseInt(line.get(4));
            int rentSet = Integer.parseInt(line.get(5));
            int rentHouse1 = Integer.parseInt(line.get(6));
            int rentHouse2 = Integer.parseInt(line.get(7));
            int rentHouse3 = Integer.parseInt(line.get(8));
            int rentHouse4 = Integer.parseInt(line.get(9));
            int rentHotel = Integer.parseInt(line.get(10));
            int buildingCost = Integer.parseInt(line.get(11));
            int mortgage = Integer.parseInt(line.get(12));
            int unMortgage = Integer.parseInt(line.get(13));
            String useName = line.get(14);
            int[] rentArr = {rent, rentSet, rentHouse1, rentHouse2, rentHouse3, rentHouse4, rentHotel};
            ColorPropertyTile add = new ColorPropertyTile(color, name, useName, price, rentArr, buildingCost,
                    mortgage, unMortgage);
            returnProperties.add(add);
        }
        return returnProperties;
    }

    /**
     * Take a CSV file with the information of RailRoad Properties and create and return RailroadTile objects.
     *
     * @param path                          the CSV location of the properties
     * @return                              return an ArrayList which includes all the Color Property Tiles
     * @throws FileNotFoundException        if the given file is not found throw this exception
     */
    public static List<RailroadTile> initializeRailRoadProperties(String path) throws FileNotFoundException {
        List<List<String>> railRoad = extractor(path);
        List<RailroadTile> returnProperties= new ArrayList<RailroadTile>();
        for(List<String> line : railRoad){
            String name = line.get(1);
            int price = Integer.parseInt(line.get(2));
            int rentOwn1 = Integer.parseInt(line.get(3));
            int rentOwn2 = Integer.parseInt(line.get(4));
            int rentOwn3 = Integer.parseInt(line.get(5));
            int rentOwn4 = Integer.parseInt(line.get(6));
            int mortgage = Integer.parseInt(line.get(7));
            int unMortgage = Integer.parseInt(line.get(8));
            String useName = line.get(9);
            int[] rentArr = {rentOwn1, rentOwn2, rentOwn3, rentOwn4};
            RailroadTile add = new RailroadTile(name, useName, price, rentArr, mortgage, unMortgage);
            returnProperties.add(add);
        }
        return returnProperties;
    }

    /**
     * Take a CSV file with the information of UtilityTile Properties and create and return UtilityTile objects.
     *
     * @param path                          the CSV location of the properties
     * @return                              return an ArrayList which includes all the Color Property Tiles
     * @throws FileNotFoundException        if the given file is not found throw this exception
     */
    public static List<UtilityTile> initializeUtilityProperties(String path) throws FileNotFoundException {
        List<List<String>> utilityProperties = extractor(path);
        List<UtilityTile> returnProperties= new ArrayList<UtilityTile>();
        for(List<String> line : utilityProperties){
            String name = line.get(1);
            int price = Integer.parseInt(line.get(2));
            int rent1 = Integer.parseInt(line.get(3));
            int rent2 = Integer.parseInt(line.get(4));
            int mortgage = Integer.parseInt(line.get(5));
            int unMortgage = Integer.parseInt(line.get(6));
            String useName = line.get(7);
            int[] rentArr = {rent1, rent2};
            UtilityTile add = new UtilityTile(name, useName, price, rentArr, mortgage, unMortgage);
            returnProperties.add(add);
        }
        return returnProperties;
    }
}
