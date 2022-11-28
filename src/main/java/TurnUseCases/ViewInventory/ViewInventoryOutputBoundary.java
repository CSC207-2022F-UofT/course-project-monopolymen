package TurnUseCases.ViewInventory;

import GameEntities.Player;

import java.util.ArrayList;

public interface ViewInventoryOutputBoundary {
    /**
     * Presents the list of players in the game
     *
     * @param listPlayers       The list of players in the game
     */
    public void showPlayers(ArrayList<Player> listPlayers);

    /**
     * Presents the inventory of the chosen Player
     *
     * @param player        The player that we are trying to view the inventory of
     */
    public void showInventory(Player player);
}
