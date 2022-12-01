package TurnUseCases.EndTurnUseCase;
import GameEntities.Player;
import java.util.ArrayList;

/**
 * Output Boundary for EndTurn UseCase, to be implemented by the Presenter
 */
public interface EndTurnOutputBoundary {
    /**
     * @param player The player that is moving or choosing an action in the current turn
     * @param flavorText String saying either the player ended their turn or is forced to end
     */
    public void showResultOfAction(Player player, String flavorText);
}