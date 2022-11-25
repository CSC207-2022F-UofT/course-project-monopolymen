package GameEntities.Tiles;

import GameEntities.Player;

public class GoToJailTile extends Tile{
    /**
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
        return new TileActionResultModel("You are being sent to jail.", player, -1);
    }
}
