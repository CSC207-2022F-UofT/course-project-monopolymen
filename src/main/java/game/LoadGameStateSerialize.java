package game;

import turn_interface_adapters.TurnController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String getSavesDirectory() {
        return savesDirectory;
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
    public GameState load(String saveName, SaveGameState saveGameState, GameStateOutputBoundary presenter, TurnController turnController) {
        try (FileInputStream fileIn = new FileInputStream(savesDirectory + saveName + ".ser")) {
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return GameState.deserialize(objectIn, saveGameState, presenter, turnController);
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

    /**
     * Get a list of all the names of the games that are currently saved.
     *
     * @return A List of String game names. Empty list if no saves or cannot access directory.
     */
    @Override
    public List<String> getAllSaves() {
        File saveDirectory = new File(savesDirectory);
        if (saveDirectory.isDirectory()) {
            ArrayList<String> saveNames = new ArrayList<>();
            for (File file : Objects.requireNonNull(saveDirectory.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".ser") && file.getName().startsWith("save_")) {
                    int substringEnd = file.getName().length() - 4;
                    String saveName = file.getName().substring(0, substringEnd).replaceFirst("^save_", "");
                    saveNames.add(saveName);
                }
            }
            return saveNames;
        }
        return new ArrayList<>();
    }
}
