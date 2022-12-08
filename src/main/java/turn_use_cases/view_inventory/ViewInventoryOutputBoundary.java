package turn_use_cases.view_inventory;

import java.util.List;
/**
 * The output boundary for the view inventory use case.
 */
public interface ViewInventoryOutputBoundary {
    /**
     * Display the inventory of the players
     *
     * @param currentName       The name of the current player
     * @param playersInfo       A List of Inventory Data objects for all the players in the game
     */
    public void showInventory(String currentName, List<InventoryData> playersInfo);

    /**
     * Display 4 buttons that when clicked will open a players inventory
     */
    public void showInventoryButtons();
}
