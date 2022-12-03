package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGameStateSerialize implements SaveGameState {
    private final String savesDirectory;

    /**
     * Construct an object that handles saving of GameState objects via serialization.
     *
     * @param savesDirectory The directory to save the gameState objects in. Trailing slash optional.
     */
    public SaveGameStateSerialize(String savesDirectory) {
        if (!savesDirectory.endsWith("/")) {
            this.savesDirectory = savesDirectory + "/";
        } else {
            this.savesDirectory = savesDirectory;
        }
    }

    public String getSavesDirectory() {
        return savesDirectory;
    }

    /**
     * Save the GameState to a file with the name <i>saveName</i>.ser in the directory
     * <i>savesDirectory</i>
     *
     * @param gameState The GameState object to save. Must be Serializable for this class.
     * @param saveName  The name of the save file.
     * @return True if gameState was successfully saved, False otherwise.
     */
    @Override
    public boolean save(GameState gameState, String saveName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(savesDirectory + saveName + ".ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gameState);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
