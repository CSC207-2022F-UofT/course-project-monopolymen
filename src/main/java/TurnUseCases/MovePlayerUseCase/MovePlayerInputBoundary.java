package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 * Includes the amount they rolled for and the player
 */
public interface MovePlayerInputBoundary {
    /**
     * @param playerRollAmount Int array of length 2 representing the roll of two die
     * @param player The player object that the action is being performed on
     */
    public void startAction(int[] playerRollAmount, Player player);
}
