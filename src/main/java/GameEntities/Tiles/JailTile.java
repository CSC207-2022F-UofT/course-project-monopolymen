package GameEntities.Tiles;

import GameEntities.Board;
import GameEntities.Player;

public class JailTile extends Tile{
    /**
     * A player landing on the space by GoToJailTile cannot move for 3 turns unless they pay or have
     * "get out of jail" card.
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
    public TileActionResultModel action(Player player, Board board) {
        String inJail = "You are in jail";
        String notInJail = "You are just visiting";
        if(player.getTurnsInJail() != -1) {
            return new TileActionResultModel(inJail, player, -1);
        } else {
            return new TileActionResultModel(notInJail, player, -1);
        }
    }
}
