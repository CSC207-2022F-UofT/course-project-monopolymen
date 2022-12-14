package game_entities.tiles;
import game_entities.Player;
/**
 * Data Model for the result of a tile's action (when a player lands on the tile).
 *
 * @see game_entities.tiles.Tile
 */
public class TileActionResultModel {

    private final String flavorText;
    private final int playerPosition;
    private final Player player;

    /**
     * Construct an object that contains data for the result of a game tile's action
     *
     * @param flavorText     the text describing the action's result
     * @param player         the player that the action is being performed on
     * @param playerPosition the integer describing the current position of the player after the action
     */
    public TileActionResultModel(String flavorText, Player player, int playerPosition) {
        this.flavorText = flavorText;
        this.player = player;
        this.playerPosition = playerPosition;

    }

    public String getFlavorText() {
        return flavorText;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public Player getPlayer() { return player; }

}
