package game;

import game_entities.Player;

import java.util.List;

public interface GameStateOutputBoundary {
    /**
     * Show that it is the next player's turn.
     *
     * @param newPlayer  The player whose turn it now is.
     * @param turnNumber The current Turn Number.
     */
    void showNextTurn(Player newPlayer, int turnNumber);

    /**
     * Show the current player the valid Turn Actions they can take. These TurnActions link to TurnController methods.
     *
     * @param currentPlayer The player whose turn it currently is.
     * @param validActions  A List describing what actions the player is able to take during their turn.
     */
    void showTurnActions(Player currentPlayer, List<TurnActions> validActions);

    /**
     * Show whether the autosave was successful.
     *
     * @param successful True if the autosave was successful. False otherwise.
     */
    void showAutosaveStatus(boolean successful);

    enum TurnActions {
        ROLL_TO_MOVE,
        BUILD_BUILDING,
        SELL_BUILDING,
        MORTGAGE,
        UNMORTGAGE,
        TRADE,
        LEAVE_JAIL,
        END_TURN
    }
}
