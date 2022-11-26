package Game;

public interface LoadGameState {
    /**
     * Load the GameState object. Assumes the save exists.
     *
     * @param saveName The name of the save to load.
     * @return The GameState object loaded in the save.
     */
    GameState load(String saveName);

    /**
     * Check if the save exists.
     *
     * @param saveName The name of the save to check.
     * @return True if the save exists. False otherwise.
     */
    boolean saveExists(String saveName);
}
