package GameEntities.Tiles;

public class GoTile extends Tile {
    /**
     * When landed on or passed, that player gets 200$. Does not happen when sent to Jail.
     *
     */
    public GoTile() {
        super("GoTile", "Go Tile");
    }


    /**
     * Give $200 to the Player <i>player</i> that lands on this tile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TileActionResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        Player.money += 200;

        return new TileActionResultModel("You got $200!", 0);
    }

    /**
     * Give $200 to the Player <i>player</i> that passes on this tile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    @Override
    public TilePassResultModel passing(Player player) {
        Player.money += 200;

        return new TilePassResultModel(true, "You got $200!");
    }
}
