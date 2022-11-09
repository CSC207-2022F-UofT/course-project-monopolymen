package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import java.lang.Math;


/**
 * All methods related to build buildings.
 */

public class BuildBuildings {

    /**
     * Check if player can build buildings
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public boolean isBuildable (Player player, ColorPropertyTile property){
        if(!player.ownsProperty(property)){
            return false;
        }
        if(property.isMortgaged()){
            return false;
        }
        //When player owns all the properties in a Monopoly color group, player can buy houses.
        String color = property.getColor(); // need to add a method getColor to get the color of the ColorPropertyTile.
        int counter = 0;
        ArrayList<Propertie> properties = player.getProperties();
        for (int i = 0; i < player.getProperties().size(); i++){
            if(properties.get(i).getColor().equals(color)) {
                counter += 1;
            }
        }
        if (color.equals("Brown") || color.equals("Dark Blue")){
            return counter == 2;
        } else{
            return counter == 3;
        }
        //Player has to build equally – this means player can’t build a second house on a property unless player has a house on all the other properties.
        ArrayList<Propertie> SameColorProperties = new ArrayList<Propertie>();;
        for (int i = 0; i < player.getProperties().size(); i++){
            if(properties.get(i).getColor().equals(color)){
                SameColorProperties.add(properties.get(i));
            }
        }
        for (Propertie sameColorProperty : SameColorProperties) {
            int a = sameColorProperty.getHouse() + sameColorProperty.getHotel();
            // need to add two methods getHouse and getHotel in ColorPropertyTile class to get the number of houses and hotels.
            int b = property.getHouse() + property.getHotel();
            if (Math.abs(a - b) > 1) {
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
    public boolean buildHouse (Player player, ColorPropertyTile property){
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
            return true;
        }
        return false;
    }

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public boolean buildHotel (Player player, ColorPropertyTile property){
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
            return true;
        }
        return false;
    }
}
