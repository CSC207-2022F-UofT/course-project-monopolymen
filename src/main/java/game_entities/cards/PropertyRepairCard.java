package game_entities.cards;

import game_entities.Player;

import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

import java.util.ArrayList;

public class PropertyRepairCard extends Card{
    private final int houseRepair;
    private final int hotelRepair;

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
        for (Property property : properties) {
            if (property instanceof ColorPropertyTile) {
                totalHouses = totalHouses + ((ColorPropertyTile) property).getNumHouses();
                totalHotels = totalHotels + ((ColorPropertyTile) property).getNumHotels();
            }
        }
        player.subtractMoney(totalHotels * this.hotelRepair + totalHouses * this.houseRepair);

        return new CardActionResultModel(getCardDescription(), player, player.getPosition(),
                getCardName(), isChanceCard());
    }

}
