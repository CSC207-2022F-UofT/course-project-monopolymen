package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

import java.lang.Math;
import java.util.ArrayList;


/**
 * All methods related to build buildings.
 */

public class BuildBuildings implements BuildBuildingInputBoundary{

    /**
     * Check if player can build buildings
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    @Override
    public boolean isBuildable (Player player, Property property){
        if(!player.ownsProperty(property)){
            return false;
        }
        if(property.isMortgaged()){
            return false;
        }
        // Selecting ColorPropertyTile
        ArrayList<ColorPropertyTile> ColorProperties = new ArrayList<ColorPropertyTile>();
        ArrayList<Property> properties = player.getProperties();
        for (int i = 0; i < properties.size(); i++){
            if(properties.get(i).getClass().getName().equals("GameEntities.Tiles.ColorPropertyTile")){
                ColorProperties.add(properties.get(i));
            }
        }
        String color = property.getColor(); // need to add a method getColor to get the color of the ColorPropertyTile.
        ArrayList<ColorPropertyTile> SameColorProperties = new ArrayList<ColorPropertyTile>();
        int counter = 0;
        for (int i = 0; i < ColorProperties.size(); i++){
            if(ColorProperties.get(i).getColor().equals(color)) {
                counter += 1;
                SameColorProperties.add(ColorProperties.get(i));
            }
        }
        //When player owns all the properties in a Monopoly color group, player can buy houses.
        if (color.equals("Brown") || color.equals("Dark Blue")){
           if (counter != 2){
               return false;
           }
        } else{
            if (counter != 3){
                return false;
            }
        }
        //Player has to build equally – this means player can’t build a second house on a property unless player has a house on all the other properties.
        for (ColorPropertyTile sameColorProperty : SameColorProperties) {
            int a = sameColorProperty.getHouse() + sameColorProperty.getHotel();
            // need to add two methods getHouse and getHotel in ColorPropertyTile class to get the number of houses and hotels.
            int b = property.getHouse() + property.getHotel();
            if (b > a) {
                return false;
            }
        }
        return true;
    }

    /**
     * Build a house on the property.
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    @Override
    public void buildHouse (Player player, ColorPropertyTile property){
        if (isBuildable(player, property)){
            property.addHouse();
            //need to add a method addHouse in ColorPropertyTile class to add the number of house.
            String color = property.getColor();
            switch (color) {
                case "Brown":
                case "Light Blue":
                    player.subtractMoney(50);
                    break;
                case "Pink":
                case "Orange":
                    player.subtractMoney(100);
                    break;
                case "Red":
                case "Yellow":
                    player.subtractMoney(150);
                    break;
                case "Green":
                case "Dark Blue":
                    player.subtractMoney(200);
                    break;
            }
        }
    }

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    @Override
    public void buildHotel (Player player, ColorPropertyTile property){
        if (isBuildable(player, property) && property.getHouses() >= 4){
            property.addHotel();
            //need to add a method addHotel in ColorPropertyTile class to add the number of hotel.
            String color = property.getColor();
            switch (color) {
                case "Brown":
                case "Light Blue":
                    player.subtractMoney(50);
                    break;
                case "Pink":
                case "Orange":
                    player.subtractMoney(100);
                    break;
                case "Red":
                case "Yellow":
                    player.subtractMoney(150);
                    break;
                case "Green":
                case "Dark Blue":
                    player.subtractMoney(200);
                    break;
            }
        }
    }
}
