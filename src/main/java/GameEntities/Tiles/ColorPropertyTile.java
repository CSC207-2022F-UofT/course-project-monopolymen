package GameEntities.Tiles;

public class ColorPropertyTile extends Property{
    private String color;

    private int rent;

    private int rentSet;

    private int rentHouse1;

    private int rentHouse2;

    private int rentHouse3;

    private int rentHouse4;

    private int rentHotel;

    private int buildingCost;

    private int mortgage;

    private int unMortgage;

    /**
     * When landed on, players can choose to buy the colored property for its printed price if it is unowned.
     * @param color The color of the tile property
     * @param propertyName The internal string name representing this tile
     *      *              (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice The price to purchase this property
     * @param rent The cost of rent if a player lands on the property
     * @param rentSet The cost of rent if a player lands on the color property if a whole color set has been owned
     *                by a player
     * @param rentHouse1 Cost of rent if a player lands on the color property if it has 1 house
     * @param rentHouse2 Cost of rent if a player lands on the color property if it has 2 house
     * @param rentHouse3 Cost of rent if a player lands on the color property if it has 3 house
     * @param rentHouse4 Cost of rent if a player lands on the color property if it has 4 house
     * @param rentHotel Cost of rent if a player lands on the color property if it has a hotel
     * @param buildingCost Cost it takes to upgrade to the next house or hotel level
     * @param mortgage The value of this property for mortgage purposes
     * @param unMortgage The amount it takes to unMortgage
     */
    public ColorPropertyTile(String color, String propertyName, String propertyDisplayName, int purchasePrice,
        int rent, int rentSet, int rentHouse1, int rentHouse2, int rentHouse3, int rentHouse4, int rentHotel,
        int buildingCost, int mortgage, int unMortgage) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgage);
        this.color = color;
        this.rent = rent;
        this.rentSet = rentSet;
        this.rentHouse1 = rentHouse1;
        this.rentHouse2 = rentHouse2;
        this.rentHouse3 = rentHouse3;
        this.rentHouse4 = rentHouse4;
        this.rentHotel = rentHotel;
        this.buildingCost = buildingCost;
        this.unMortgage = unMortgage;
    }


}

