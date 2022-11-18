package TurnUseCases.TryToGetOutOfJailUseCase;

import GameEntities.Player;
import GameEntities.Tiles.TilePassResultModel;

public interface TryToGetOutOfJailOutputBoundary {
    /**
     * Shows the result of the player landing on the tile
     * @param player The player object the action is being performed on
     * @param playerPosition The end position the player is supposed to be
     */
    public void showResultOfAction(Player player, int playerPosition);

    /**
     * Shows the result of player passing the tile
     * @param player The player object the action is being performed on
     * @param playerPosition The player's position
     * @param tilePassResultModel The data model of what happens when a player passes the tile
     */

    public void showResultOfPass(Player player, int playerPosition, TilePassResultModel tilePassResultModel);

    /**
     * Shows the roll the player made
     * @param playerRollAmount The array of two elements representing the players rolls
     */
    public void showRoll(int[] playerRollAmount);
}
