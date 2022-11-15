package GameEntities.Tiles;

import GameEntities.Player;

import java.util.List;

import static java.lang.Math.min;

public class RailroadTile extends Property{
    private final int[] rentPrice;

    /**
     * When landed on, players can choose to buy the railroad property for its printed price if it is unowned.
     *
     * @param rentPrice           An array specifying the rent where the index denotes the number of railroads owned
     *                            besides this one (ex. rentPrice[1] is the rent if the owner of this railroad owns 2
     *                            total railroads).
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param mortgageValue       The value of this property for mortgage purposes
     * @see GameEntities.Tiles.Tile
     */
    public RailroadTile(int[] rentPrice, String propertyName, String propertyDisplayName, int purchasePrice,
                        int mortgageValue, int unMortgageValue) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgageValue, unMortgageValue);
        this.rentPrice = rentPrice;
    }

    /**
     * Returns the number of railroads that the owner of this Property owns in propertyList.
     * Does not count this property if it is not in propertyList
     * If there is no owner, the return value is 0.
     *
     * @param propertyList The list of Property objects to search through
     * @return The number of railroads that this Property's owner owns in total. 0 if no owner of this Property.
     */
    public int numRailroadOwned(List<Property> propertyList) {
        if (!isOwned()) {
            return 0;
        }

        int numOwned = 0;
        for (Property property : propertyList) {
            if (property instanceof RailroadTile &&
                    property.isOwned() &&
                    property.getOwner().equals(getOwner())) {
                numOwned++;
            }
        }
        return numOwned;
    }

    /**
     * Return the rent for this railroad property.
     * Railroad rent is influenced by how many railroads are owned by one person.
     * If this property is unowned, returns -1.
     *
     * @param rentPayer    The Player who is paying the rent.
     * @param propertyList The list of properties to check for other railroads in.
     * @return The integer rent value.
     */
    public int getRent(Player rentPayer, List<Property> propertyList) {
        if (!isOwned()) {
            return -1;
        }

        int numOwned = numRailroadOwned(null);
        // If we have the unintended effect of more railroads owned than accounted for, return the last rent value.
        numOwned = min(numOwned - 1, rentPrice.length - 1);
        return rentPrice[numOwned];
    }

    /**
     * Not Implemented, leaving implementation for Youssef
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return a TileActionResultModel object describing the action taken.
     */
    @Override
    public TileActionResultModel action(Player player) {
        // Leaving proper implementation for Youssef so he has code he can implement
        return null;
    }

}
