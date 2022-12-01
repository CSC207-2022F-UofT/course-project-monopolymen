package turn_use_cases.move_player_use_case;
import game_entities.Player;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 */
public interface MovePlayerInputBoundary {
    /**
     * Starts the logic of rolling the dice and moving the player
     * @param player The player object that the action is being performed on
     */
    public void startAction(Player player);
}
