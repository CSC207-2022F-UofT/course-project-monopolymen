package TurnUseCases.ViewInventory;

import GameEntities.Player;

import java.util.List;
/**
 * The output boundary for the view inventory use case.
 */
public interface ViewInventoryOutputBoundary {
    /**
     * Display the inventory of the players
     *
     * @param current           The player who has requested to view their inventory
     * @param playersInfo       A List of Inventory Data objects for all of the players in the game
     */
    public void showInventory(Player current, List<InventoryData> playersInfo);
}
