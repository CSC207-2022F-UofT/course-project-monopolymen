package GameEntities.Tiles;

public class FreeParkingTile extends Tile{
    /**
     * When a player lands Free Parking Tile nothing happens, and they move off the space on their next turn.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    protected FreeParkingTile(String tileName, String tileDisplayName, boolean ownable) {
        super("FreeParkingTile", "Free Parking Tile", false);
    }

    /**
     * Nothing happens.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TileActionResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        return new TileActionResultModel("You land on free parking tile! Nothing happens!", 0);
    }

}
