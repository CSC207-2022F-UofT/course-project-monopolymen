package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

import java.util.ArrayList;


/**
 * All methods related to build buildings.
 */

public class BuildBuildings implements BuildBuildingInputBoundary{

    /**
     * Check if player can build buildings
     *
     * @param player the player who wants  to build buildings.
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
        if(!(property instanceof ColorPropertyTile)){
            return false;
        }
        // Selecting ColorPropertyTile from player's properties.
        ArrayList<ColorPropertyTile> ColorProperties = new ArrayList<ColorPropertyTile>();
        ArrayList<Property> properties = player.getProperties();
        for (int i = 0; i < properties.size(); i++){
            if(properties.get(i).getClass().getName().equals("GameEntities.Tiles.ColorPropertyTile")){
                ColorProperties.add(properties.get(i));
            }
        }
        // Selecting ColorPropertyTile with the same color.
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
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     * @param presenter presenter which provides info for players.
     */
    @Override
    public void buildHouse (Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter){
        if (isBuildable(player, property)){
            property.addHouse();
            //need to add a method addHouse in ColorPropertyTile class to add the number of house.
            String color = property.getColor();
            player.subtractMoney(property.getBuildingCost);
            String text = player.getName() + "built a house on " + property.getTileName();
            presenter.showBuildBuilding(player, property, text);
        }
        String text = player.getName() + "cannot build a house on " + property.getTileName();
        presenter.showBuildBuilding(player, property, text);
    }

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     * @param presenter presenter which provides info for players.
     */
    @Override
    public void buildHotel (Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter){
        if (isBuildable(player, property) && property.getHouses() >= 4){
            property.addHotel();
            //need to add a method addHotel in ColorPropertyTile class to add the number of hotel.
            String color = property.getColor();
            player.subtractMoney(property.getBuildingCost);
            String text = player.getName() + "built a hotel on " + property.getTileName();
            presenter.showBuildBuilding(player, property, text);
        }
        String text = player.getName() + "cannot build a hotel on " + property.getTileName();
        presenter.showBuildBuilding(player, property, text);
    }

    /**
     * Check if player can sell buildings
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    @Override
    public boolean isSellable(Player player, Property property) {
        if(!player.ownsProperty(property)){
            return false;
        }
        if(!(property instanceof ColorPropertyTile)){
            return false;
        }
        ArrayList<ColorPropertyTile> ColorProperties = new ArrayList<ColorPropertyTile>();
        ArrayList<Property> properties = player.getProperties();
        for (int i = 0; i < properties.size(); i++){
            if(properties.get(i).getClass().getName().equals("GameEntities.Tiles.ColorPropertyTile")){
                ColorProperties.add(properties.get(i));
            }
        }
        // Selecting ColorPropertyTile with the same color.
        String color = property.getColor();
        ArrayList<ColorPropertyTile> SameColorProperties = new ArrayList<ColorPropertyTile>();
        for (int i = 0; i < ColorProperties.size(); i++){
            if(ColorProperties.get(i).getColor().equals(color)) {
                SameColorProperties.add(ColorProperties.get(i));
            }
        }
        for (ColorPropertyTile sameColorProperty : SameColorProperties) {
            int a = sameColorProperty.getHouse() + sameColorProperty.getHotel();
            int b = property.getHouse() + property.getHotel();
            if (b < a) {
                return false;
            }
        }
        return true;
    }

    /**
     * Selling a house on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     * @param presenter presenter which provides info for players
     */
    @Override
    public void sellHouse(Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter){
        if (isSellable(player, property) && property.getHotel() == 0){
            property.subtractHouse();
            int value = 0;
            value = 0.5*property.getBuildingCost();
            player.addMoney(value);
            String text = player.getName() + "sold a house on " + property.getTileName();
            presenter.showSellBuilding(player, property, text);
        }
        String text = player.getName() + "cannot sell a house on " + property.getTileName();
        presenter.showSellBuilding(player, property, text);
    }

    /**
     * Selling a hotel on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     * @param presenter presenter which provides info for players
     */
    @Override
    public void sellHotel(Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter){
        if (isSellable(player, property) && property.getHotel() > 0){
            property.subtractHotel();
            int value = 0;
            value = 0.5*property.getBuildingCost();
            player.addMoney(value);
            String text = player.getName() + "sold a hotel on " + property.getTileName();
            presenter.showSellBuilding(player, property, text);
        }
        String text = player.getName() + "cannot sell a hotel on " + property.getTileName();
        presenter.showSellBuilding(player, property, text);
    }
}
