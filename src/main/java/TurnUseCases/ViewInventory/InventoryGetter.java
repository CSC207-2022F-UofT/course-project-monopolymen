package TurnUseCases.ViewInventory;

import GameEntities.Player;
import GameEntities.Tiles.Property;

import java.util.ArrayList;

/**
 *  Gets all the information that needs to be displayed for a Player and puts it in an InventoryGetter object
 *  The information that is going to be displayed:
 *      1) Players Money
 *      2) Players Property List
 *      3) Players get out of Jail free card if they own any
 */
public class InventoryGetter {
    final int money;
    final int getOutofJailFree;
    final ArrayList<Property> properties;

    /**
     * Create an Object consisting of the information that we need to display for a given player
     *
     * @param player        The player that we will be using to create this object
     */
    public InventoryGetter(Player player){
        this.money = player.getMoney();
        this.getOutofJailFree = player.numGetOutoffJailFreeCards();
        this.properties = player.getProperties();
    }
}
