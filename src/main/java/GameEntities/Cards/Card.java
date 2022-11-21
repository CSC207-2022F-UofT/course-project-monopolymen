package GameEntities.Cards;

import GameEntities.Player;

public abstract class Card {
    private final String cardName;

    private final String cardDisplayName;

    private final String flavourText;

    private final boolean chanceCard; //If false, then it's a community card.

    public Card(String cardName, String cardDisplayName, String flavourText, boolean chanceCard) {
        this.cardName = cardName;
        this.cardDisplayName = cardDisplayName;
        this.flavourText = flavourText;
        this.chanceCard = chanceCard;
    }


    public abstract void cardAction(Player player);

    public String getCardName() {
        return cardName;
    }
}

