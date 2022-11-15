package GameEntities.Tiles;

import java.util.ArrayList;

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

    /**
     * Returns the number of railroads that the owner of this Property owns in propertyList.
     * Does not count this property if it is not in propertyList
     * If there is no owner, the return value is 0.
     * @param propertyList The list of Property objects to search through
     * @return The number of railroads that this Property's owner owns in total. 0 if no owner of this Property.
     */
    public int numRailroadOwned(ArrayList<Property> propertyList) {
        if (getOwner() == null) {
            return 0;
        }

        int numOwned = 0;
        for(Property property : propertyList) {
            if(property instanceof RailroadTile &&
                    property.getOwner() != null &&
                    property.getOwner().equals(getOwner())) {
                numOwned++;
            }
        }
        return numOwned;
    }

}
