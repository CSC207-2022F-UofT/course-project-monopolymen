package turn_use_cases.view_inventory;

import game_entities.Player;
import game_entities.tiles.Property;

import java.util.ArrayList;

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
    }
}
