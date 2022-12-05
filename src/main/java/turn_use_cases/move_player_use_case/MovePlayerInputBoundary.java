package turn_use_cases.move_player_use_case;
import game_entities.Player;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 */
public interface MovePlayerInputBoundary {
    /**
     * Starts the logic of rolling the dice
     * @param player The player object that the action is being performed on
     * @param canRollAgain Default value is true. If true, the player will be prompted to roll again if they roll a double.
     */
    public void startAction(Player player, boolean canRollAgain);

    /**
     * This is only called by startAction when it lands on a draw card tile that will move the player
     * @param player The player object that the action is being performed on
     * @param rollSum The sum of the dice roll
     * @param doubleRoll If the player rolled a double
     */
    public void movePlayer(Player player, int rollSum, boolean doubleRoll);
}
