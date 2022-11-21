package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 */
public interface MovePlayerInputBoundary {
    /**
     * Starts the logic of rolling the dice and moving the player
     * @param player The player object that the action is being performed on
     */
    public void startAction(Player player);

    /**
     * Starts the logic of moving the player to the position
     * This is only called by startAction when it lands on a draw card tile that will move the player
     * @param player The player object that the action is being performed on
     * @param absolutePosition the position the player will move to
     */
    public void moveToPosition(Player player, int absolutePosition);
}
