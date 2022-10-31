package GameEntities.Tiles;

import GameEntities.Player;

public class FreeParkingTile extends Tile{
    /**
     * Construct a new Free Parking tile.
     * When a player lands Free Parking Tile nothing happens, and they move off the space on their next turn.
     * @see GameEntities.Tiles.Property
     */
    public FreeParkingTile() {
        super("FreeParkingTile", "Free Parking Tile");
    }

    /**
     * Nothing happens.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TileActionResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        return new TileActionResultModel("You land on free parking tile! Nothing happens!", player.getPosition());
    }

}
