package turn_use_cases.end_turn_use_case;
import game_entities.Player;

/**
 * Output Boundary for EndTurn UseCase, to be implemented by the Presenter
 */
public interface EndTurnOutputBoundary {
    /**
     * @param player The player that is moving or choosing an action in the current turn
     * @param flavorText String saying either the player ended their turn or is forced to end
     */
    void showResultOfAction(Player player, String flavorText);
}