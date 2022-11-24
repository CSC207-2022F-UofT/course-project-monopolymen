package GameEntities.Cards;

import GameEntities.Player;

public abstract class Card {
    private final String cardName;

    private final String cardDisplayName;

    private final String cardDescription;

    private final boolean chanceCard; //If false, then it's a community card.

    public Card(String cardName, String cardDisplayName, String flavourText, boolean chanceCard) {
        this.cardName = cardName;
        this.cardDisplayName = cardDisplayName;
        this.cardDescription = flavourText;
        this.chanceCard = chanceCard;
    }

    public abstract CardActionResultModel action(Player player);
    public String getCardName() {
        return cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public boolean isChanceCard() {
        return chanceCard;
    }
}

