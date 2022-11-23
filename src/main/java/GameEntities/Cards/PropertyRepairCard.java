package GameEntities.Cards;

import GameEntities.Player;

import GameEntities.Tiles.Property;

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
        ArrayList<Property> pProperties = player.getProperties();
//        for(int i = 0; i < pProperties.size(); i++){
//            totalHouses = totalHouses + pProperties.get(i).getHouses();
//            totalHotels = totalHotels + pProperties.get(i).getHotels();
//        } the getHouses and getHotels methods haven't been made yet
        player.subtractMoney(totalHotels * -this.hotelRepair);
        player.subtractMoney(totalHouses * -this.houseRepair);

        CardActionResultModel result = new CardActionResultModel(getCardDescription(), player, player.getPosition(),
                getCardName());
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
