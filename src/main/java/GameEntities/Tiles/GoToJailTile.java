package GameEntities.Tiles;

import GameEntities.Player;

public class GoToJailTile extends Tile{
    /**
     * A player landing on the space will be put into jail.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    public GoToJailTile() {
        super("GoToJailTile", "Go To jail Tile");
    }

    /**
     * When lands on this tile, Player <i>player</i> moves to JailTile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        player.addTurnInJail();
        return new TileActionResultModel(player, -1);
    }
}
