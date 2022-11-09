package GameEntities.Tiles;

import GameEntities.Player;

public class JailTile extends Tile{
    /**
     * A player landing on the space cannot move for 3 turns unless they pay or have "get out of jail" card.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    public JailTile() {
        super("JailTile", "jail Tile");
    }

    /**
     * When lands on this tile, Player <i>player</i> cannot move for the next 3 turns unless they pay $50 to the bank
     * or owns and uses a "get out of jail" card
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        return new TileActionResultModel("You are just visiting jail", -1);
    }
}
