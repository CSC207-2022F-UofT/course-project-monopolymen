package TurnUseCases.EndTurnUseCase;
import GameEntities.Player;
import java.util.ArrayList;

/**
 * Output Boundary for EndTurn UseCase, to be implemented by the Presenter
 */
public interface EndTurnOutputBoundary {
    /**
     * @param player The player that is moving or choosing an action in the current turn
     * @param playerOptions String ArrayList of options the current player can choose
     */
    public void showResultOfAction(Player player, ArrayList<String> playerOptions);
}