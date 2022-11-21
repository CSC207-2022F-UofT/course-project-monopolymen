package TurnUseCases.TradeUseCase;

import GameEntities.Player;
import GameEntities.Tiles.Property;

import java.util.ArrayList;

/**
 * Contains the details of the trade player1 wants to make with player2.
 */

public class TradeOffer {

    // The amount of money player1 is offering player2 if positive,
    // the amount of money player1 wants from player2 if negative.
    private int tradeMoney;

    // If 1, player1 is offering a get out of jail card to player2.
    // If 0, player1 is not offering or requesting a get out of jail card from player2.
    // If -1, player1 is requesting a get out of jail card from player2.
    private int jailCard;

    // The list of properties player1 is offering to player2.
    private ArrayList<Property> propertiesOffered;

    // The list of properties player1 wants to receive from player2.
    private ArrayList<Property> propertiesReceived;

    // The player who is making the trade offer.
    private Player player1;

    // The player who is receiving the trade offer.
    private Player player2;

    // Says if this trade offer is valid or not.
    private boolean isValid;

    /**
     * Creates a new TradeOffer.
     *
     * @param tradeMoney if positive, it is the amount of money player1 is offering player2.
     *                   If negative, it is the amount of money player1 wants from player2.
     * @param jailCard if 1, then player1 is offering a get out of jail card to player2.
     *                 If -1, then player1 is requesting a get out of jail card from player2.
     *                 If 0, then no get out of jail cards are offered or requested.
     * @param propertiesOffered the list of properties player1 is offering player2.
     * @param propertiesReceived the list of properties player1 wants from player2.
     * @param player1 the player who made the trade offer.
     * @param player2 the player who is receiving the trade offer.
     */
    public TradeOffer(int tradeMoney, int jailCard,
                      ArrayList<Property> propertiesOffered, ArrayList<Property> propertiesReceived,
                      Player player1, Player player2){
        this.tradeMoney = tradeMoney;
        this.jailCard = jailCard;
        this.propertiesOffered = propertiesOffered;
        this.propertiesReceived = propertiesReceived;
        this.player1 = player1;
        this.player2 = player2;
        this.isValid = checkIsValid();

    }

    /**
     * Checks if this TradeOffer is valid or not.
     *
     * @return Whether this TradeOffer is valid or not.
     */
    public boolean checkIsValid(){
        if(tradeMoney > 0 && player1.getMoney() < tradeMoney){
            return false;
        } else if (tradeMoney < 0 && player2.getMoney() < - tradeMoney) {
            return false;
        } else if (!checkPropertiesOffered()) {
            return false;
        } else if (!checkPropertiesReceived()) {
            return false;
        } else {
            return checkJailCard();
        }

    }

    /**
     * Checks if player1 actually owns all the properties he/she is offering.
     *
     * @return whether player1 owns all the properties offered or not.
     */
    public boolean checkPropertiesOffered(){
        for (Property p : propertiesOffered){
            if(!player1.getProperties().contains(p)){
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if player2 actually owns all the properties he/she is offering.
     *
     * @return whether player2 owns all the properties offered or not.
     */
    public boolean checkPropertiesReceived(){
        for (Property p : propertiesReceived){
            if(!player2.getProperties().contains(p)){
                return false;
            }
        }

        return true;
    }


    /**
     * Checks if the player offering the jail card has one.
     *
     * @return whether the player offering the jail card has one.
     */
    public boolean checkJailCard(){
        if (getJailCard() > 0 && !player1.getGetOutOfJailCard()){
            return false;
        } else return !(getJailCard() < 0 && !player2.getGetOutOfJailCard());
    }

    public int getTradeMoney() {
        return tradeMoney;
    }

    public int getJailCard() {
        return jailCard;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Property> getPropertiesOffered() {
        return propertiesOffered;
    }

    public ArrayList<Property> getPropertiesReceived() {
        return propertiesReceived;
    }

    public boolean isValid() {
        return isValid;
    }
}
