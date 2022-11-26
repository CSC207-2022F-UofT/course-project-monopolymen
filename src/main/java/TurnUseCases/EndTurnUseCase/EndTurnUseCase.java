package TurnUseCases.EndTurnUseCase;
import GameEntities.Player;
import java.util.ArrayList;

/**
 * EndTurnUseCase (Class to handle ending the player's turn and allowing the next player to move)
 * MovePlayerUseCase will handle the cases when the player either chooses to end their own turn or if an event in the
 * game forcefully ends their turn.
 */
public class EndTurnUseCase implements EndTurnInputBoundary{

    private EndTurnOutputBoundary endTurnOutputBoundary;

    /**
     * @param endTurnOutputBoundary EndTurnOutputBoundary to handle display
     */
    public EndTurnUseCase(EndTurnOutputBoundary endTurnOutputBoundary) {
        this.endTurnOutputBoundary = endTurnOutputBoundary;
    }

    @Override
    public void endTurn(Player player) {
        endTurnOutputBoundary.showResultOfAction(player, "You ended your turn.");
    }

    @Override
    public void forceEndTurn(Player player) {
        endTurnOutputBoundary.showResultOfAction(player, "Your turn has been ended");
    }
}
