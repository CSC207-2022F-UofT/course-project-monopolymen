package GameEntities.Tiles;

import GameEntities.Player;

import java.util.ArrayList;
import java.util.List;

public class ColorPropertyTile extends Property{
    private String color;

    private final int[] rentPrice;

    private int buildingCost;


    /**
     * When landed on, players can choose to buy the colored property for its printed price if it is unowned.
     *
     * @param color               The color of the tile property
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param rentPrice           An array specifying the rent where the index denotes the number of color properties owned
     *                            belonging to the same set as this property. (ex. rentPrice[1] is the rent if the owner of this
     *                            ColorPropertyTile owns 2 total ColorPropertyTiles of the set <i>color</i>).
     * @param buildingCost        Cost it takes to upgrade to the next house or hotel level
     * @param mortgage            The value of this property for mortgage purposes
     * @param unMortgage          The amount it takes to unMortgage
     */
    public ColorPropertyTile(String color, String propertyName, String propertyDisplayName, int purchasePrice,
                             int[] rentPrice, int buildingCost, int mortgage, int unMortgage) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgage, unMortgage);
        this.color = color;
        this.rentPrice = rentPrice;
        this.buildingCost = buildingCost;
    }

    public String getColor() {
        return color;
    }

    /**
     * Return the rent for this ColorPropertyTile property.
     * ColorPropertyTile rent is influenced by whether the owner owns all the properties in a set and how many
     * buildings (houses/hotels) are on the ColorPropertyTile property.
     * If this property is unowned, returns -1.
     *
     * @param rentPayer    The Player who is paying the rent.
     * @param propertyList The list of properties to search for other ColorPropertyTiles in.
     * @return The int rent value of this property
     */
    @Override
    public int getRent(Player rentPayer, List<Property> propertyList) {
        return 0;
    }

    /**
     * TODO not implemented (leaving for Youssef).
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TileActionResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        return null;
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public boolean checkSetOwned(Property[] arr) {
        boolean ownSet = true;
        ArrayList<Player> playerArr = new ArrayList<Player>();
        for (Property property : arr) {
            if(property instanceof ColorPropertyTile) {
                if(((ColorPropertyTile) property).getColor() == this.color) {
                    playerArr.add(property.getOwner());
                }
            }
        }
        Player firstPlayer = playerArr.get(0);
        for(Player player: playerArr) {
            if(!player.equals(firstPlayer)) {
                ownSet = false;
            }
        }
        return ownSet;
    }
}

