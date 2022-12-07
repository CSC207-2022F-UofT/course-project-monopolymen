package turn_use_cases.view_inventory;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.RailroadTile;
import game_entities.tiles.UtilityTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  Gets all the information that needs to be displayed for a Player and puts it in an InventoryGetter object
 *  The information that is going to be displayed:
 *      1) Players Money
 *      2) Players Property List
 *      3) Players get out of Jail free card if they own any
 *      4) Players name
 */
public class InventoryData {
    final int money;
    final int getOutofJailFree;
    final ArrayList<Property> properties;
    final String name;

    final String icon;

    /**
     * Create an Object consisting of the information that we need to display for a given player
     *
     * @param player        The player that we will be using to create this object
     */
    public InventoryData(Player player){
        this.money = player.getMoney();
        this.getOutofJailFree = player.numGetOutofJailFreeCards();
        this.properties = player.getProperties();
        this.name = player.getName();
        this.icon = player.getIcon();
    }

    /**
     * Add the cards that the player owns in a set in a List and return a List of Lists
     *
     * @return      A List of List with each List containing only cards in one set
     */
    public static List<List<Property>> sortProperties(InventoryData player){
        List<List<Property>> sorted = new ArrayList<List<Property>>();
        List<Property> railRoadProperties = new ArrayList<Property>();
        List<Property> utilityProperties = new ArrayList<Property>();
        List<String> colors = new ArrayList<String>();
        for(Property x : player.properties){
            if(x instanceof RailroadTile){
                railRoadProperties.add(x);
            } else if (x instanceof UtilityTile) {
                utilityProperties.add(x);
            } else {
                ColorPropertyTile thisProperty = (ColorPropertyTile) x;
                String color = thisProperty.getColor();
                if (colors.contains(color)){
                    for(List<Property> name : sorted){
                        ColorPropertyTile sample = (ColorPropertyTile) name.get(0);
                        if(Objects.equals(sample.getColor(), color)){
                            name.add(thisProperty);
                        }
                    }
                } else{
                    colors.add(color);
                    List<Property> newList = new ArrayList<Property>();
                    newList.add(thisProperty);
                    sorted.add(newList);
                }
            }
        }
        sorted.add(railRoadProperties);
        sorted.add(utilityProperties);
        return sorted;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {return money;}
    public static List<InventoryData> creator(List<Player> playerList){
        List<InventoryData> returnList = new ArrayList<InventoryData>();
        for (Player player : playerList){
            returnList.add(new InventoryData(player));
        }
        return returnList;
    }

    public int getGetOutofJailFree() {
        return getOutofJailFree;
    }
}
