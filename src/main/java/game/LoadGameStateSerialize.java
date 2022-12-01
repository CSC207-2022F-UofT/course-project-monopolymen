package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGameStateSerialize implements LoadGameState {
    private final String savesDirectory;

    /**
     * Construct an object that handles loading of serialized GameState objects.
     *
     * @param savesDirectory The directory to save the gameState objects in. Trailing slash optional.
     */
    public LoadGameStateSerialize(String savesDirectory) {
        if (!savesDirectory.endsWith("/")) {
            this.savesDirectory = savesDirectory + "/";
        } else {
            this.savesDirectory = savesDirectory;
        }
    }

    /**
     * Load the GameState object. Assumes the save exists.
     * The SaveGameState data gateway and the GameStateOutputBoundary presenter are not serialized and so must be given
     * back to the GameState object.
     *
     * @param saveName      The name of the save to load.
     * @param saveGameState The SaveGameState object responsible for saving the GameState object.
     * @param presenter     The presenter object for GameState.
     * @return The GameState object loaded in the save.
     */
    @Override
    public GameState load(String saveName, SaveGameState saveGameState, GameStateOutputBoundary presenter) {
        try (FileInputStream fileIn = new FileInputStream(savesDirectory + saveName + ".ser")) {
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return GameState.deserialize(objectIn, saveGameState, presenter);
        } catch (IOException e) {
            throw new RuntimeException("Save Not Found", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("GameState class not found. Invalid build.", e);
        }
    }

    /**
     * Check if the save exists.
     *
     * @param saveName The name of the save to check.
     * @return True if the save exists. False otherwise.
     */
    @Override
    public boolean saveExists(String saveName) {
        File save = new File(savesDirectory + saveName + ".ser");
        return save.isFile();
    }
}
