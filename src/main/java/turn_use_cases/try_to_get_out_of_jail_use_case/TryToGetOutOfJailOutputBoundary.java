package turn_use_cases.try_to_get_out_of_jail_use_case;

import game_entities.Player;

import java.util.ArrayList;

/**
 * Output Boundary for TryToGetOutOfJail UseCase, to be implemented by the Presenter
 */
public interface TryToGetOutOfJailOutputBoundary {

    /**
     * Shows the options the player can choose
     * @param player The player Object
     * @param options String arraylist for what the player can choose
     */
     void showOptions(Player player, ArrayList<String> options);

    /**
     * Shows what the player rolled
     * @param playerRollAmount int array of what the player rolled
     */
     void showRoll(int[] playerRollAmount);
}
