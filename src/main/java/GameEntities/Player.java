package GameEntities;
import GameEntities.Tiles.*;
import java.util.ArrayList;
/**
 * A game of monopoly will have 4 players each responsible for keeping track of information such as
 * their money, position and so on...
 */
public class Player {
    private String name;
    private int money;
    private ArrayList<Property> properties = new ArrayList<Property>();
    private int turnsInJail = -1;
    private int position = 0;
    private String icon;
    private int[] lastRoll = {0, 0};
    private int consecutiveDoubles = 0;

    private int getOutOfJailFree = 0;

    /**
     * The Player can choose their name and icon but the other attributes are set to default values
     *
     * @param iconInput     string representing the icon the player has chosen to be
     *
     * @param nameInput     string representing the name the player has chosen
     *
     * @param money         int representing the amount of money a player starts off with
     */
    public Player(String nameInput, String iconInput, int money){
        this.name = nameInput;
        this.icon = iconInput;
        this.money = money;
    }

    //getters:

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public ArrayList<Property> getProperties() {
        return this.properties;
    }

    public int getTurnsInJail() {
        return this.turnsInJail;
    }

    public int getPosition() {
        return this.position;
    }

    public String getIcon() {
        return this.icon;
    }

    public int[] getLastRoll() {
        return this.lastRoll;
    }

    public int getConsecutiveDoubles() {
        return this.consecutiveDoubles;
    }

    //setters:
    /**
     * Set the last roll of the player so that it can be used for actions that might happen during the turn
     *
     * @param roll1         int representing the value of the first dice
     *
     * @param roll2         int representing the value of the second dice
     */
    public void setLastRoll(int roll1, int roll2){
        this.lastRoll[0] = roll1;
        this.lastRoll[1] = roll2;
    }

    //methods:

    /**
     * Add an amount of money to the players total
     * @param add       int representing the amount of money that we are trying to add
     */
    public void addMoney(int add){
        this.money += add;
    }


    /**
     * Attempt to subtract an amount of money from the players total
     *
     * @param subtract      int representing the amount of money we are trying to subtract
     * @return              return true if the player had enough money to make the payment and false if they do not
     */
    public boolean subtractMoney(int subtract){
        if(this.money >= subtract){
            this.money -= subtract;
            return true;
        }
        return false;
    }

    /**
     * Check if a player owns a property
     *
     * @param check     Property that we are trying to check if the player owns
     * @return          True if the player owns property and false if they do not
     */

    public boolean ownsProperty(Property check){
        return this.properties.contains(check);
    }

    /**
     * Add a property to a players property ArrayList
     *
     * @param add       Property that we are trying to add to the players owned properties ArrayList
     */
    public void addProperty(Property add){
        this.properties.add(add);
    }

    /**
     * Remove a given property from a players property ArrayList
     *
     * @param sell      Property that the player is trying to sell
     */
    public void sellProperty(Property sell){
        this.properties.remove(sell);
    }

    /**
     * This method will be called every turn a player is in the jail to update the in jail turn tracker
     */
    public void addTurnInJail(){
        this.turnsInJail += 1;
    }

    /**
     * Called when the player has left the jail to reset the number of turn spent in jail to -1 which just states that
     * the player is not in jail
     */
    public void resetTurnInJail(){
        this.turnsInJail = -1;
    }

    /**
     * Called when a player enters the jail, updates the position of the player to the jail position and adds a turn in
     * jail. It will also reset consecutive doubles to 0
     *
     * @param board         the board that the game is being played on
     */
    public void enterJail(Board board){
        this.addTurnInJail();
        this.position = board.getJailTilePosition();
        this.resetConesecutiveDoubles();
    }

    /**
     * Update the position of a player given the sum of the dies they have rolled
     *
     * @param rollSum       the sum of the two dies the player has rolled
     * @param board         the board that the game is being played on
     */
    public void updatePosition(int rollSum, Board board) {
        this.position = (this.position + rollSum) % board.getTilesList().size();
    }

    /**
     * Checks if a player has a get out of jail free card
     *
     * @return      True if the player owns a get out of jail free card
     */
    public boolean hasGetOutofJailFreeCard(){
        return (this.getOutOfJailFree != 0);
    }

    /**
     * Remove 1 get out of jail free card from the players inventory
     */
    public void removeGetOutOfJailCard(){
        this.getOutOfJailFree -= 1;
    }

    /**
     * Adds one get out of jail free card to the players inventory
     */
    public void addGetOutOfJailCard() {this.getOutOfJailFree += 1;}

    /**
     * Take in a players two die rolls and update the consecutive doubles attributes if they are equal
     *
     * @param roll1     int representing the value of the first dice roll
     * @param roll2     int representing the value of the second dice roll
     */
    public void updateConsecutiveDoubles(int roll1, int roll2){
        if(roll1 == roll2){
            this.consecutiveDoubles += 1;
        }
    }

    /**
     * Reset the consecutive doubles rolled by the player to 0
     */
    public void resetConesecutiveDoubles(){
        this.consecutiveDoubles = 0;
    }
}

