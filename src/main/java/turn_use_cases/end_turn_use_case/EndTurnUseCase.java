package turn_use_cases.end_turn_use_case;
import game.GameState;
import game_entities.Player;

/**
 * end_turn_use_case (Class to handle ending the player's turn and allowing the next player to move)
 * move_player_use_case will handle the cases when the player either chooses to end their own turn or if an event in the
 * game forcefully ends their turn.
 */
public class EndTurnUseCase implements EndTurnInputBoundary{

    private final GameState gameState;

    /**
     * @param endTurnOutputBoundary EndTurnOutputBoundary to handle display
     * @param gameState             Controller keeping track of turn rotation and current turn
     */
    public EndTurnUseCase(EndTurnOutputBoundary endTurnOutputBoundary, GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void endTurn(Player player) {
        gameState.endTurn();
        // Removed endTurn presenter for now because it was calling turnController.endTurn infinitely.
//        endTurnOutputBoundary.showResultOfAction(player, " ended their turn");
    }

    @Override
    public void forceEndTurn(Player player) {
        gameState.endTurn();
//        endTurnOutputBoundary.showResultOfAction(player, "'s turn has been ended");
    }
}