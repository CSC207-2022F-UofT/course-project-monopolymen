package GameEntities.Cards;

import GameEntities.Player;

import GameEntities.Tiles.Property;

import java.util.ArrayList;

public class PropertyRepairCard extends Card{
    private int houseRepair;
    private int hotelRepair;

    protected PropertyRepairCard(String cardName, String cardDisplayName, boolean ownable,
                                 String flavourText, boolean chanceCard, int houseRepair, int hotelRepair) {
        super(cardName, cardDisplayName, flavourText, ownable, chanceCard);
        this.hotelRepair = hotelRepair;
        this.houseRepair = houseRepair;
    }

    @Override
    public void cardAction(Player player) {
        int totalHouses = 0;
        int totalHotels = 0;
        ArrayList<Property> pProperties = player.getProperties();
//        for(int i = 0; i < pProperties.size(); i++){
//            totalHouses = totalHouses + pProperties.get(i).getHouses();
//            totalHotels = totalHotels + pProperties.get(i).getHotels();
//        } the getHouses and getHotels methods haven't been made yet
        player.addMoney(totalHotels * -hotelRepair);
        player.addMoney(totalHouses * -houseRepair);
    }
}
