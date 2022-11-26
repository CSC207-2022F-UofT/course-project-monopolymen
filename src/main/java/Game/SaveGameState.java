package Game;

public interface SaveGameState {
    /**
     * Save the GameState object.
     *
     * @param gameState The GameState object to save.
     * @param saveName  The name associated with the save. If save already exists, this will overwrite the save.
     * @return True if the save was successful. False otherwise.
     */
    boolean save(GameState gameState, String saveName);
}
