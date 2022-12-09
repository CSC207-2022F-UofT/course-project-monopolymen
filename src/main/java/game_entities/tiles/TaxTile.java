package game_entities.tiles;

import game_entities.Board;
import game_entities.Player;

public class TaxTile extends Tile{

    private final int taxAmount;
    /**
     * A player landing on the space must pay a income tax or luxury tax to the Bank.
     * @see game_entities.tiles.Property
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

