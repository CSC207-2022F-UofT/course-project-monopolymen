package TurnUseCases.ViewInventory;

import GameEntities.Player;

import java.util.ArrayList;

public class ViewInventory implements ViewInventoryInputBoundary{

    @Override
    public Player[] players(GameState gamestate) {
        return gamestate.getPlayers();
    }

    @Override
    public InventoryGetter displayerInfo(Player player) {
        return new InventoryGetter(player);
    }
}
