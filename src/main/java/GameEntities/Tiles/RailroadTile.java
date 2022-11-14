package GameEntities.Tiles;

public class RailroadTile extends Property{
    private int[] rentPrice;
    /**
     * When landed on, players can choose to buy the rail road property for its printed price if it is unowned.
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param mortgageValue       The value of this property for mortgage purposes
     * @see GameEntities.Tiles.Tile
     */
    public RailroadTile(int[] rentPrice, String propertyName, String propertyDisplayName, int purchasePrice, int mortgageValue) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgageValue);
        this.rentPrice = rentPrice;
    }
    public int checkNumOwnedByPlayer(){

    }

}
