package TurnUseCases.EndTurnUseCase;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 */
public interface EndTurnInputBoundary {
    /**
     * End the player's turn. The GameState class will handle the logic and presenter call.
     * To be used only when a player chooses to end their turn.
     */
    public void endTurn();

    /**
     * Forcefully End the player's turn. The GameState class will handle the logic and presenter call.
     * To be used only when an event in the game ends the players turn, for example player landing on "Go to jail" tile.
     */
    public void forceEndTurn();
}
