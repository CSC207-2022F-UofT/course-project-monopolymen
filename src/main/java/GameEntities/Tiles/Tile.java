package GameEntities.Tiles;

import GameEntities.Board;
import GameEntities.Player;

/**
 * Tiles are spaces that players can land on in a game of monopoly.
 * All tiles have action methods intended for what happens when a player lands on the tile.
 * All tiles have passing methods intended for what happens when a player passes the tile (or lands on it).
 */
public abstract class Tile {
    private final String tileName;
    private final String tileDisplayName;
    private final boolean ownable;

    /**
     * Tiles are spaces on the board that players land on and may have effects when landed on or when passed.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     */
    public Tile(String tileName, String tileDisplayName) {
        this.tileName = tileName;
        this.tileDisplayName = tileDisplayName;
        this.ownable = false;
    }

    /**
     * Tiles are spaces on the board that players land on and may have effects when landed on or when passed.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    protected Tile(String tileName, String tileDisplayName, boolean ownable) {
        this.tileName = tileName;
        this.tileDisplayName = tileDisplayName;
        this.ownable = ownable;
    }

    /**
     * Perform the action for when Player <i>player</i> lands on this tile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TileActionResultModel object describing the action that was performed
     */
    public abstract TileActionResultModel action(Player player, Board board);

    /**
     * Perform the action for when Player <i>player</i> passes this tile.
     * Players that land on this tile are also "passing" the tile and so have this method called
     * <p>
     * By default, no action is taken
     * (actionTaken is an attribute of TilePassResultModel to indicate if something did happen).
     *
     * @param player The Player that the passing action is being performed on
     * @return A TilePassResultModel object describing the passing action that was performed
     */
    public TilePassResultModel passing(Player player) {
        return new TilePassResultModel(false, "");
    }

    public String getTileName() {
        return tileName;
    }

    public String getTileDisplayName() {
        return tileDisplayName;
    }

    public boolean isOwnable() {
        return ownable;
    }
}
