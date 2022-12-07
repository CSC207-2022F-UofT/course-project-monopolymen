package game_entities.tiles;

import game_entities.Board;
import game_entities.Player;

import java.util.List;

import static java.lang.Math.min;

public class UtilityTile extends Property {
    private final int[] rentFactor;

    /**
     * Construct a Utility Property Tile.
     * When landed on, players can choose to buy the utility for its printed price.
     *
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param rentFactor          An array specifying the multiplicative factor where the index denotes the number
     *                            of utilities owned besides this one (ex. rentFactor[1] is the rate that the dice
     *                            roll is multiplied by to determine the rent paid).
     * @param mortgageValue       The value of this property for Mortgaging purposes
     * @param unMortgageValue     The amount it takes to unMortgage this property
     * @see game_entities.tiles.Property
     */
    public UtilityTile(String propertyName, String propertyDisplayName, int purchasePrice, int[] rentFactor,
                       int mortgageValue, int unMortgageValue) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgageValue, unMortgageValue);
        this.rentFactor = rentFactor;
    }

    /**
     * Returns the number of utilities that the owner of this Property owns in propertyList.
     * Does not count this property if it is not in propertyList
     * If there is no owner, the return value is 0.
     *
     * @param propertyList The list of Property objects to search through
     * @return The number of utilities that this Property's owner owns in total. 0 if no owner of this Property.
     */
    public int numUtilityOwned(List<Property> propertyList) {
        if (!isOwned()) {
            return 0;
        }

        int numOwned = 0;
        for (Property property : propertyList) {
            if (property instanceof UtilityTile &&
                    property.isOwned() &&
                    property.getOwner().equals(getOwner())) {
                numOwned++;
            }
        }
        return numOwned;
    }

    /**
     * Get the Rent value for this Utility.
     * Rent value is a multiplicative factor (depending on the number of utilities owned) times the dice roll from
     * rentPayer's last roll.
     *
     * @param rentPayer    The Player who is paying the rent.
     * @param propertyList The list of properties to search for other utilities in.
     * @return The int rent value of this property
     */
    @Override
    public int getRent(Player rentPayer, List<Property> propertyList) {
        int numOwned = numUtilityOwned(propertyList);
        // If we have the unintended effect of more railroads owned than accounted for, return the last rent value.
        numOwned = min(numOwned - 1, rentFactor.length - 1);

        int diceRollSum = 0;
        for (int value : rentPayer.getLastRoll()) {
            diceRollSum += value;
        }

        return rentFactor[numOwned] * diceRollSum;
    }

    /**
     * Perform the action for when Player <i>player</i> lands on this tile.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TileActionResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player, Board board) {
        if (!isOwned()){
            return new TileActionResultModel("Would you Like to Purchase " + getTileDisplayName() + " for" + getPurchasePrice() + " ?" , player, player.getPosition());
        }
        else{
            player.subtractMoney(getRent(player, board.getPropertyTiles()));
            getOwner().addMoney(getRent(player, board.getPropertyTiles()));
            return new TileActionResultModel("You Paid" + getRent(player, board.getPropertyTiles()) + " to" + getOwner().getName(), player, player.getPosition());
        }
    }
}
