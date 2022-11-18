package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;
import GameEntities.Tiles.TilePassResultModel;

/**
 * Output Boundary for MovePlayer UseCase, to be implemented by the Presenter
 */
public interface MovePlayerOutputBoundary {
    /**
     * @param player The player object the action is being performed on
     * @param playerPosition The end position the player is supposed to be
     * @param rollAgain Boolean indicating if the player rolled a double and is able to roll again
     */
    public void showResultOfAction(Player player, int playerPosition, boolean rollAgain);

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
