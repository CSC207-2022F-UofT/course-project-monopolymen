package GameEntities.Tiles;
import GameEntities.Player;
/**
 * Data Model for the result of a tile's action (when a player lands on the tile).
 *
 * @see GameEntities.Tiles.Tile
 */
public class TileActionResultModel {

    private final String flavorText;
    private final int playerPosition;
    private final Player player;
    private final int moveToPosition;

    /**
     * Construct an object that contains data for the result of a game tile's action
     *
     * @param flavorText     the text describing the action's result
     * @param playerPosition the integer describing the final position of the player after the action
     */
    public TileActionResultModel(String flavorText, int playerPosition) {
        this.flavorText = flavorText;
        this.playerPosition = playerPosition;
    }

    public TileActionResultModel(Player player, int moveToPosition) {
        this.player = player;
        this.moveToPosition = moveToPosition;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

}
