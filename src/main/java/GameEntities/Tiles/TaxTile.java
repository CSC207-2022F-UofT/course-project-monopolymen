package GameEntities.Tiles;

import GameEntities.Player;

public class TaxTile extends Tile{

    private int taxAmount;
    /**
     * A player landing on the space must pay a income tax or luxury tax to the Bank.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    public TaxTile(String tileName, String tileDisplayName, int taxAmount) {
        super(tileName, tileDisplayName);
        this.taxAmount = taxAmount;
    }

    /**
     * Player <i>player</i> pays tax when lands on tax tile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    public TileActionResultModel action(Player player) {
        player.subtractMoney(taxAmount);
        return new TileActionResultModel("You paid $" + taxAmount + getTileDisplayName() + " !", player.getPosition());
    }
}

