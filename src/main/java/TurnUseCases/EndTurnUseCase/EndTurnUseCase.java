package TurnUseCases.EndTurnUseCase;
import Game.GameState;
import GameEntities.Player;

/**
 * EndTurnUseCase (Class to handle ending the player's turn and allowing the next player to move)
 * MovePlayerUseCase will handle the cases when the player either chooses to end their own turn or if an event in the
 * game forcefully ends their turn.
 */
public class EndTurnUseCase implements EndTurnInputBoundary{

    private EndTurnOutputBoundary endTurnOutputBoundary;
    private GameState gameState;

    /**
     * @param endTurnOutputBoundary EndTurnOutputBoundary to handle display
     * @param gameState             Controller keeping track of turn rotation and current turn
     */
    public EndTurnUseCase(EndTurnOutputBoundary endTurnOutputBoundary, GameState gameState) {
        this.endTurnOutputBoundary = endTurnOutputBoundary;
        this.gameState = gameState;
    }

    @Override
    public void endTurn(Player player) {
        gameState.endTurn();
        endTurnOutputBoundary.showResultOfAction(player, "ended their turn");
    }

    @Override
    public void forceEndTurn(Player player) {
        gameState.endTurn();
        endTurnOutputBoundary.showResultOfAction(player, "'s turn has been ended");
    }
}