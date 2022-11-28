package TurnUseCases.ViewInventory;

import GameEntities.Player;

import java.util.List;

/**
 * The input boundary for the view inventory use case.
 */

public interface ViewInventoryInputBoundary {

    /**
     * Pass the information that is needed to be displayed to the presenter
     *
     * @param currentPlayer     The player whose turn it is
     * @param players           A list of all the players
     */
    public void displayInfo(Player currentPlayer, List<Player> players);
}
