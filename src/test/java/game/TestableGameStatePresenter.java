package game;

import game_entities.Player;

import java.util.List;

/**
 * This presenter is for testing purpose only and has no implementation.
 */
class TestableGameStatePresenter implements GameStateOutputBoundary {
    @Override
    public void showNextTurn(Player newPlayer, int turnNumber) {

    }

    @Override
    public void showTurnActions(Player currentPlayer, List<TurnActions> validActions) {

    }

    @Override
    public void showAutosaveStatus(boolean successful) {

    }
}
