package GameEntities.Tiles;

/**
 * Data Model for the result of a player passing a tile.
 * Players that land on a tile are also "Passing" the tile.
 * Generally nothing happens when a player passes a tile.
 *
 * @see GameEntities.Tiles.Tile
 */
public class TilePassResultModel {

    private final boolean actionTaken;
    private final String flavorText;

    /**
     * Construct an object that contains data for the result of passing a game tile.
     * Players that land on a tile are also "Passing" the tile.
     *
     * @param actionTaken whether an action was taken when the player passed the tile
     * @param flavorText  the text describing the action's result
     */
    public TilePassResultModel(boolean actionTaken, String flavorText) {
        this.actionTaken = actionTaken;
        this.flavorText = flavorText;
    }

    public boolean isActionTaken() {
        return actionTaken;
    }

    public String getFlavorText() {
        return flavorText;
    }
}
