package game_entities.tiles;

import game_entities.Board;
import game_entities.Player;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ColorPropertyTile extends Property{
    private String color;

    private final int[] rentPrice;

    private int buildingCost;

    private int numHouses;

    private int numHotels;


    /**
     * Construct a Colored Property Tile.
     * When landed on, players can choose to buy the color property for its printed price.
     *
     * @param color               The color of the tile property
     * @param propertyName        The internal string name representing this tile
     *                            (intended to not contain spaces or other special characters).
     * @param propertyDisplayName The string name displayed to the user. This may have special characters.
     * @param purchasePrice       The price to purchase this property
     * @param rentPrice           An array specifying the rent where the index denotes a specific rent price depending on
     *                            whether a color property set or buildings are owned.
     * @param buildingCost        Cost it takes to upgrade to the next house or hotel level
     * @param mortgageValue       The value of this property for mortgage purposes
     * @param unMortgageValue     The amount it takes to unMortgage
     * @param numHouses           The number of houses in a given colored property tile
     * @param numHotels           The number of hotels in a given colored property tile
     * @see game_entities.tiles.Property
     */
    public ColorPropertyTile(String color, String propertyName, String propertyDisplayName, int purchasePrice,
                             int[] rentPrice, int buildingCost, int mortgageValue, int unMortgageValue) {
        super(propertyName, propertyDisplayName, purchasePrice, mortgageValue, unMortgageValue);
        this.color = color;
        this.rentPrice = rentPrice;
        this.buildingCost = buildingCost;
        this.numHouses = 0;
        this.numHotels = 0;


    }

    public String getColor() {
        return color;
    }

    public int getNumHouses() {return numHouses;}
    public int getNumHotels() {return numHotels;}
    public void addHouse(int add){
        this.numHouses += add;
    }
    public void addHotel(int add){
        this.numHotels += add;
    }
    public void subtractHouse(int subtract){
        this.numHouses -= subtract;
        if (this.numHouses < 0){
            this.numHouses = 0;
        }
    }
    public void subtractHotel(int subtract){
        this.numHotels -= subtract;
        if (this.numHotels < 0){
            this.numHotels = 0;
        }

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
        if (!isOwned()){
            return -1;
        }
        if ((getNumHotels() == 0 && getNumHouses() == 0) & checkSetOwned(propertyList)){
            return rentPrice[1];
        }
        switch(getNumHouses()){
            case 1:
                return rentPrice[2];
            case 2:
                return rentPrice[3];
            case 3:
                return rentPrice[4];
            case 4:
                return rentPrice[5];
        }
        if (getNumHotels() == 1){
            return rentPrice[6];
        }
        return rentPrice[0];
    }

    /**
     * Perform the action for when Player <i>player</i> lands on this tile.
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

    public int getBuildingCost() {
        return buildingCost;
    }

    public boolean checkSetOwned(List<Property> arr) {
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

