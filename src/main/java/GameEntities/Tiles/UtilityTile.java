package GameEntities.Tiles;

public class UtilityTile extends Property{
    private final int[] rentPrice;

    /**
     * Construct a Utility Property Tile.
     * When landed on, players can choose to buy the utility for its printed price.
     *
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param mortgageValue       The value of this property for Mortgaging purposes
     * @param unMortgageValue     The value of this property for unMortgaging purposes
     * @see GameEntities.Tiles.Tile
     */
    public UtilityTile(int[] rentPrice, String propertyName, String propertyDisplayName, int purchasePrice,
                       int mortgageValue, int unMortgageValue) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgageValue, unMortgageValue);
        this.rentPrice = rentPrice;
    }
}
