package GameEntities;

import java.util.ArrayList;
/**
 *
 */
public class Player {
    private String nameq;
    private int money = 1500;
    private ArrayList<Propertie> properties;
    private int turnsInJail = -1;
    private int position = 0;
    private String icon;
    private int[] lastRoll = {0, 0};
    private int consecutiveDoubles = 0;

    public Player(String nameInput, String iconInput){
        this.name = nameInput;
        this.icon = iconInput;
    }

    //getters:

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public ArrayList<Propertie> getProperties() {
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

    public void setLastRoll(int roll1, int roll2){
        this.lastRoll[0] = roll1;
        this.lastRoll[2] = roll2;
    }

    //methods:

    public void addMoney(int add){
        this.money += add;
    }

    public boolean subtractMoney(int subtract){
        if(this.money >= subtract){
            this.money -= subtract;
            return true;
        }
        return false;
    }

    public boolean ownsProperty(Propertie check){
        return this.properties.contains(check);
    }

    public void addProperty(Propertie add){
        this.properties.add(add);
    }

    public void sellProperty(Propertie sell){
        this.properties.remove(sell);
    }

    public void addTurnInJail(){
        //do we set this to 0 or to 1 when the player first goes to jail
        this.turnsInJail += 1;
    }

    public void resetTrunInJail(){
        this.turnsInJail = -1;
    }

    public void updatePosition(int rollSum) {
        if (this.position + rollSum <= 29) {
            this.position += rollSum;
        } else if (this.position + rollSum == 30) {
            this.position = 0;
        } else{
            rollSum = rollSum - (30 - this.position);
            this.position = rollSum;
        }
    }

    public void updateConsecutiveDoubles(boolean doubleRoll){
        if(doubleRoll){
            this.consecutiveDoubles += 1;
        }else{
            this.consecutiveDoubles = 0;
        }
    }
}

