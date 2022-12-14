package game;

import turn_interface_adapters.TurnController;

import java.util.List;

public interface LoadGameState {
    /**
     * Load the GameState object. Assumes the save exists.
     * The SaveGameState data gateway and the GameStateOutputBoundary presenter are not serialized and os must be given
     * back to the GameState object.
     *
     * @param saveName      The name of the save to load.
     * @param saveGameState The SaveGameState object responsible for saving the GameState object.
     * @param presenter     The presenter object for GameState.
     * @return The GameState object loaded in the save.
     */
    GameState load(String saveName, SaveGameState saveGameState, GameStateOutputBoundary presenter, TurnController turnController);

    /**
     * Check if the save exists.
     *
     * @param saveName The name of the save to check.
     * @return True if the save exists. False otherwise.
     */
    boolean saveExists(String saveName);

    /**
     * Get a list of all the names of the games that are currently saved.
     *
     * @return A List of String game names.
     */
    List<String> getAllSaves();

    String getSavesDirectory();
}
