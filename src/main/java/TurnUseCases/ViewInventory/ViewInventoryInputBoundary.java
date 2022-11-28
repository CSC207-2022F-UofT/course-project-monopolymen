package TurnUseCases.ViewInventory;

import GameEntities.Player;

import java.util.ArrayList;

/**
 * The input boundary for the view inventory use case.
 */

public interface ViewInventoryInputBoundary {
    /**
     * Returns a list of all the players in the game to the presenter.
     * This is so that the players can choose which players inventory they want to view
     *
     * @return      Return an ArrayList of the players in the game
     */
    public Player[] players(GameState gamestate);

    /**
     * Provides the presenter with the information that needs to be displayed
     *
     * @param player    The player that we want to display the information for
     * @return          A InventoryGetter object that contains the information that we need to display
     */
    public InventoryGetter displayerInfo(Player player);
}
