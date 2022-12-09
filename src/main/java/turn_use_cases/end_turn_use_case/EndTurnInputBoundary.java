package turn_use_cases.end_turn_use_case;

import game_entities.Player;

/**
 * Input Boundary for MovePlayer UseCase, to be implemented by the UseCase
 */
public interface EndTurnInputBoundary {
    /**
     * End the player's turn. The GameState class will handle the logic and presenter call.
     * To be used only when a player chooses to end their turn.
     */
    void endTurn(Player player);

    /**
     * Forcefully End the player's turn. The GameState class will handle the logic and presenter call.
     * To be used only when an event in the game ends the players turn, for example player landing on "Go to jail" tile.
     */
    void forceEndTurn(Player player);
}
