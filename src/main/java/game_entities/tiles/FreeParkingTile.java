package game_entities.tiles;

import game_entities.Board;
import game_entities.Player;

public class FreeParkingTile extends Tile{
    /**
     * Construct a new Free Parking tile.
     * When a player lands Free Parking Tile nothing happens, and they move off the space on their next turn.
     * @see game_entities.tiles.Property
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
    public TileActionResultModel action(Player player, Board board) {
        return new TileActionResultModel("You land on free parking tile! Nothing happens!", player,
                player.getPosition());
    }

}
