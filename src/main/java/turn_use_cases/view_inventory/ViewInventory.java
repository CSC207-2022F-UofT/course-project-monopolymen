package turn_use_cases.view_inventory;

import game_entities.Player;

import java.util.ArrayList;

import java.util.List;

public class ViewInventory implements ViewInventoryInputBoundary{
    private final ViewInventoryOutputBoundary presenter;

    /** Create a view_inventory object
     *
     * @param presenter     The presenter that we are using
     */
    public ViewInventory(ViewInventoryOutputBoundary presenter){
        this.presenter = presenter;
    }

    /**
     * Create a List of InventoryData given from the List of players and pass that to the presenter
     *
     * @param currentPlayer     The player whose turn it is
     * @param players           A list of all the players
     */
    @Override
    public void displayInfo(Player currentPlayer, List<Player> players) {
        List<InventoryData> playerData = new ArrayList<InventoryData>();
        for (Player player : players){
            playerData.add(new InventoryData(player));
        }
        presenter.showInventory(currentPlayer, playerData);
    }
}
