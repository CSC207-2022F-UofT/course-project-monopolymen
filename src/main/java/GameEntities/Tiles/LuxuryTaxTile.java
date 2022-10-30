package GameEntities.Tiles;

public class LuxuryTaxTile extends Tile{
    /**
     * A player landing on the space must pay a luxury tax to the Bank.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    protected LuxuryTaxTile(String tileName, String tileDisplayName, boolean ownable) {
        super("LuxuryTaxTile", "Luxury Tax Tile", false);
    }

    /**
     * Player <i>player</i> pays $100 luxury tax when lands on this tile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        Player.money -= 100;
        return new TileActionResultModel("You paid $100 luxury tax!", 0);
    }
}
