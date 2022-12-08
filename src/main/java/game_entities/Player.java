package game_entities;

import game.GameState;
import game_entities.tiles.Property;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsInputBoundary;
import turn_use_cases.liquidate_assets_use_case.LiquidateAssetsUseCase;
import turn_use_cases.liquidate_assets_use_case.LiquiditySituation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A game of monopoly will have 4 players each responsible for keeping track of information such as
 * their money, position and so on...
 */
public class Player implements Serializable {
    private String name;
    private int money;
    private ArrayList<Property> properties = new ArrayList<Property>();
    private int turnsInJail = -1;
    private int position = 0;
    private String icon;
    private int[] lastRoll = {0, 0};
    private int consecutiveDoubles = 0;

    private int getOutOfJailFree = 0;

    private Board board;

    private transient LiquidateAssetsInputBoundary liquidateAssetsInputBoundary;
    private GameState gameState;

    /**
     * The Player can choose their name and icon but the other attributes are set to default values
     *
     * @param iconInput     string representing the icon the player has chosen to be
     *
     * @param nameInput     string representing the name the player has chosen
     *
     * @param money         int representing the amount of money a player starts off with
     *
     * @param board         board representing the board the player is currently playing on
     */
    public Player(String nameInput, String iconInput, int money, Board board){
        this.name = nameInput;
        this.icon = iconInput;
        this.money = money;
        this.board = board;
    }

    public void setLiquidateAssetsInputBoundary(LiquidateAssetsInputBoundary liquidateAssetsInputBoundary) {
        this.liquidateAssetsInputBoundary = liquidateAssetsInputBoundary;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
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

    public void subtractMoney(int subtract){
        this.subtractMoney(subtract, null);
    }
    public void subtractMoney(int subtract, Player owedPlayer){
        if(subtract <= this.money) {
            this.money -= subtract;
        }
        else{
            LiquiditySituation situation = new LiquiditySituation(this, owedPlayer, subtract, this.gameState, this.board);
            liquidateAssetsInputBoundary.getPlayerOptions(situation);
        }
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
        add.setOwner(this);
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
        if (this.turnsInJail == 2){
            this.turnsInJail = -1;
            this.resetConsecutiveDoubles();
        }else {
            this.turnsInJail += 1;
        }
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
     */
    public void enterJail(){
        this.addTurnInJail();
        this.position = this.board.getJailTilePosition();
        this.resetConsecutiveDoubles();
    }

    /**
     * Update the position of a player given the sum of the dies they have rolled
     *
     * @param rollSum       the sum of the two dies the player has rolled
     */
    public void updatePosition(int rollSum) {
        this.position = (this.position + rollSum) % this.board.getTilesList().size();
    }

    /**
     * Set the position of the player to a given position
     *
     * @param position      the position that the player is being moved to
     */
    public void setPosition(int position){
        this.position = position;
    }

    /**
     * Checks if a player has a get out of jail free card
     *
     * @return      True if the player owns a get out of jail free card
     */
    public boolean hasGetOutofJailFreeCard(){
        return (this.getOutOfJailFree != 0);
    }

    /** Return the number of get out of jail free cards a player has
     *
     * @return      The number of get out of jail free cards the player owns
     */
    public int numGetOutofJailFreeCards(){ return this.getOutOfJailFree;}
    /**
     * Remove 1 get out of jail free card from the players inventory
     */
    public void removeGetOutOfJailCard() {
        this.getOutOfJailFree -= 1;
    }

    /**
     * Adds one get out of jail free card to the players inventory
     */
    public void addGetOutOfJailCard() {
        this.getOutOfJailFree += 1;
    }

    /**
     * Returns whether this Player equals the other object. Two Players are considered equal if their attributes are
     * equal (excluding the board).
     */
    @Override
    public boolean equals(Object o) {
        // Generated by Intellij
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return money == player.money && turnsInJail == player.turnsInJail && position == player.position && consecutiveDoubles == player.consecutiveDoubles && getOutOfJailFree == player.getOutOfJailFree && name.equals(player.name) && properties.equals(player.properties) && icon.equals(player.icon) && Arrays.equals(lastRoll, player.lastRoll);
    }

    @Override
    public int hashCode() {
        // Generated by Intellij
        int result = Objects.hash(name, money, properties, turnsInJail, position, icon, consecutiveDoubles, getOutOfJailFree);
        result = 31 * result + Arrays.hashCode(lastRoll);
        return result;
    }

    /**
     * Take in a players two die rolls and update the consecutive doubles attributes if they are equal
     *
     * @param roll1 int representing the value of the first dice roll
     * @param roll2 int representing the value of the second dice roll
     */
    public void updateConsecutiveDoubles(int roll1, int roll2) {
        if (roll1 == roll2) {
            this.consecutiveDoubles += 1;
        }else{
            this.resetConsecutiveDoubles();
        }
    }

    /**
     * Reset the consecutive doubles rolled by the player to 0
     */
    public void resetConsecutiveDoubles(){
        this.consecutiveDoubles = 0;
    }
}

