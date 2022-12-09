package turn_use_cases.try_to_get_out_of_jail_use_case;

import game_entities.Player;

/**
 * Input Boundary for TryToGetOutOfJail UseCase, to be implemented by the UseCase
 */
public interface TryToGetOutOfJailInputBoundary {
    /**
     * @param playerOption the choice the player chose
     * @param player The player object that the action is being performed on
     */
    void startAction(String playerOption, Player player);

    /**
     * Returns a string array of possible options the player can choose, This method should be called first
     * instead of startAction to determine appropriate actions to present
     * @param player The player object that the action is being performed on
     */
    void getPlayerOptions(Player player);
}
