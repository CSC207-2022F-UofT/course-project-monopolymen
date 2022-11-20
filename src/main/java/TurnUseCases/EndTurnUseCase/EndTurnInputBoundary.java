package TurnUseCases.EndTurnUseCase;

import GameEntities.Player;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 */
public interface EndTurnInputBoundary {
    /**
     * End the player's turn.
     * To be used only when a player chooses to end their turn.
     * <dt><b>Precondition:</b><dd>
     * The current turn is of the player in the argument
     * @param player The player object that the action is being performed on
     */
    public void endTurn(Player player);

    /**
     * Forcefully End the player's turn.
     * To be used only when an event in the game ends the players turn, for example player landing on "Go to jail" tile.
     * <dt><b>Precondition:</b><dd>
     * The current turn is of the player in the argument
     * @param player The player object that the action is being performed on
     */
    public void forceEndTurn(Player player);
}
