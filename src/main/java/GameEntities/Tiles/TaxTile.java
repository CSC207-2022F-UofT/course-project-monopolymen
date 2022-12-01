package GameEntities.Tiles;

import GameEntities.Board;
import GameEntities.Player;

public class TaxTile extends Tile{

    private int taxAmount;
    /**
     * A player landing on the space must pay a income tax or luxury tax to the Bank.
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
    public TileActionResultModel action(Player player, Board board) {
        player.subtractMoney(taxAmount);
        return new TileActionResultModel("You paid $" + taxAmount + getTileDisplayName() + " !", player,
                player.getPosition());
    }
}

