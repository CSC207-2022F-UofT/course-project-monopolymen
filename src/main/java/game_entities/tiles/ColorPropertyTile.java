package game_entities.tiles;

import game_entities.Board;
import game_entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    }
    public void subtractHotel(int subtract){
        this.numHotels -= subtract;

    }
    /**
     * Returns whether all of the colored property in a specific colored set are owned.
     * Does not count this property if it is not in propertyList
     * If there is no owner, returns false.
     *
     * @param propertyList The list of Property objects to search through
     * @return a boolean describing whether all of the colored property set is owned or not.
     */
    public boolean allColoredPropertySetOwned(String myColor, List<Property> propertyList) {
        if (!isOwned()) {
            return false;
        }
        int numOwned = 0;
        for (Property property : propertyList) {
            if (property instanceof ColorPropertyTile &&
                    property.isOwned() && getColor().equals(myColor) &&
                    property.getOwner().equals(getOwner())) {
                numOwned++;
            }
        }
        switch (myColor) {
            case "Brown":
                if (numOwned == 2) {
                    return true;
                    }
            case "Dark Blue":
                if (numOwned == 2) {
                    return true;
                }
        }
        if (numOwned == 3){
            return true;
        }
        return false;
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
            return 0;
        }
        if ((getNumHotels() == 0 && getNumHouses() == 0) & allColoredPropertySetOwned(getColor(),propertyList)){
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
            return new TileActionResultModel("Would you Like to Purchase " + getTileName() + " for " + getPurchasePrice() + " ?" , player, player.getPosition());
        }
        else{
            if(getRent(player, board.getPropertyTiles()) <= player.getMoney()){
                getOwner().addMoney(getRent(player, board.getPropertyTiles()));
            }
            player.subtractMoney(getRent(player, board.getPropertyTiles()), getOwner());
            return new TileActionResultModel("You Paid " + getRent(player, board.getPropertyTiles()) + " to " + getOwner().getName(), player, player.getPosition());
        }
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public boolean checkSetOwned(List<Property> arr) {
        ArrayList<Player> playerArr = new ArrayList<>();
        ArrayList<ColorPropertyTile> sameColor = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            if(arr.get(i) instanceof ColorPropertyTile && Objects.equals(((ColorPropertyTile) arr.get(i)).getColor(), this.color)){
                sameColor.add((ColorPropertyTile) arr.get(i));
            }
        }
        for (int i = 0; i < sameColor.size(); i++) {
            if(sameColor.get(i).isOwned()){
                playerArr.add(sameColor.get(i).getOwner());
            } else {
                return false;
            }
        }
        Player firstPlayer = playerArr.get(0);
        for(Player player: playerArr) {
            if(!player.equals(firstPlayer)) {
                return false;
            }
        }
        return true;
//        for (int i = 0; i < arr.size(); i++) {
//            if(arr.get(i) instanceof ColorPropertyTile) {
//                if(((ColorPropertyTile) arr.get(i)).getColor() == this.color) {
//                    sameColor.add((ColorPropertyTile) arr.get(i));
//                    if(!arr.get(i).isOwned()){
//                        return false;
//                    } else{
//                        playerArr.add(arr.get(i).getOwner());
//                    }
//                }
//            }
//        }
    }

}

