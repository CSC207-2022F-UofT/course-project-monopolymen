package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        //When player owns all the properties in a Monopoly color group, player can buy houses.
        String color = property.getColor();
        int counter = 0;
        ArrayList<Propertie> properties = player.getProperties();
        for (int i = 0; i < player.getProperties().size(); i++){
            properties.get(i).getColor().equals(color);
            counter += 1;
        }
        if (color.equals("Brown") || color.equals("Dark Blue")){
            return counter == 2;
        } else{
            return counter == 3;
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
            property.houses += 1;
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
            property.hotels += 1;
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
