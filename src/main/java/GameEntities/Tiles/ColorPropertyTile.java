package GameEntities.Tiles;

public class ColorPropertyTile extends Property{
    private String color;
    /**
     * When landed on, players can choose to buy the colored property for its printed price if it is unowned.
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param mortgageValue       The value of this property for mortgage purposes
     * @see GameEntities.Tiles.Tile
     */
    public ColorPropertyTile(String color, String propertyName, String propertyDisplayName, int purchasePrice, int mortgageValue){
        super("ColorPropertyTile", "Color Property Tile", 0, 0);
        this.color = color;


    }


}

