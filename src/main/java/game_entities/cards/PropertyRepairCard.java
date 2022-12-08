package game_entities.cards;

import game_entities.Player;

import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

import java.util.ArrayList;

public class PropertyRepairCard extends Card{
    private int houseRepair;
    private int hotelRepair;

    public PropertyRepairCard(String cardName, String cardDisplayName,
                                 String flavourText, boolean chanceCard, int houseRepair, int hotelRepair) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
        this.hotelRepair = hotelRepair;
        this.houseRepair = houseRepair;
    }

    @Override
    public CardActionResultModel action(Player player) {
        int totalHouses = 0;
        int totalHotels = 0;
        ArrayList<Property> properties = player.getProperties();
        for(int i = 0; i < properties.size(); i++){
            Property property = properties.get(i);
            if(property instanceof ColorPropertyTile){
                totalHouses = totalHouses + ((ColorPropertyTile) property).getNumHouses();
                totalHotels = totalHotels + ((ColorPropertyTile) property).getNumHotels();
            }
        }
        player.subtractMoney(totalHotels * this.hotelRepair + totalHouses * this.houseRepair);

        CardActionResultModel result = new CardActionResultModel(getCardDescription(), player, player.getPosition(),
                getCardName(), isChanceCard());
        return result;
    }

    /**
     * Creates a loop that looks at each property the player owns and counts how many total houses
     * and hotels the player has on their properties. Once the loop finishes counting how many houses
     * and hotels the player has, it uses the hotelRepair and houseRepair attributes of the card to
     * and multiplies the amount of
     * @param player
     */

}
