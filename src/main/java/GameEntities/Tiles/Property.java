package GameEntities.Tiles;

import GameEntities.Player;

import java.util.List;

/**
 * Property (Ownable Monopoly Tile that can be purchased and rented)
 * All Properties have rent, purchase prices, mortgaging, and owners.
 */
public abstract class Property extends Tile {
    private final int purchasePrice;
    private final int mortgageValue;
    private final int unMortgageValue;
    private boolean mortgaged;
    private Player owner;

    /**
     * Construct a Property (Ownable Monopoly Tile that can be purchased and rented)
     * Sets the unmortgage value to the full mortgage value.
     *
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param mortgageValue       The value of this property for mortgage purposes
     * @see GameEntities.Tiles.Tile
     */
    public Property(String propertyName, String propertyDisplayName, int purchasePrice, int mortgageValue) {
        this(propertyName, propertyDisplayName, purchasePrice, mortgageValue, mortgageValue);
    }

    /**
     * Construct a Property (Ownable Monopoly Tile that can be purchased and rented)
     *
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param mortgageValue       The value of this property for Mortgaging purposes
     * @param unMortgageValue     The value of this property for unMortgaging purposes
     * @see GameEntities.Tiles.Tile
     */
    public Property(String propertyName, String propertyDisplayName, int purchasePrice, int mortgageValue,
                    int unMortgageValue) {
        super(propertyName, propertyDisplayName, true);
        this.purchasePrice = purchasePrice;
        this.mortgageValue = mortgageValue;
        this.unMortgageValue = unMortgageValue;
        this.mortgaged = false;
        this.owner = null;
    }

    /**
     * Get the Rent value for this property
     *
     * @param rentPayer The Player who is paying the rent.
     * @return The int rent value of this property
     */
    public abstract int getRent(Player rentPayer, List<Property> propertyList);

    /**
     * Mortgage this Property and return the mortgage value.
     *
     * @return The int mortgage value of the property
     */
    public int mortgage() {
        this.mortgaged = true;
        return mortgageValue;
    }

    /**
     * Unmortgage this Property.
     */
    public void unmortgage() {
        this.mortgaged = false;
    }


    public int getPurchasePrice() {
        return purchasePrice;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public int getUnMortgageValue() {
        return unMortgageValue;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public boolean isOwned() {
        return owner != null;
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Set the owner of this property
     *
     * @param owner The Player who now owns this property
     * @return The previous Player who owned this property or null if no player previously owned this property.
     */
    public Player setOwner(Player owner) {
        Player prevOwner = this.owner;
        this.owner = owner;
        return prevOwner;
    }
}
