package GameEntities.Cards;

import GameEntities.Player;

public abstract class Card {
    private final String cardName;

    private final String cardDisplayName;

    private final String flavourText;

    private final boolean ownable; //for jail card

    private final boolean chanceCard; //If false, then it's a community card.

    protected Card(String cardName, String cardDisplayName, String flavourText, boolean ownable, boolean chanceCard) {
        this.cardName = cardName;
        this.cardDisplayName = cardDisplayName;
        this.flavourText = flavourText;
        this.ownable = ownable;
        this.chanceCard = chanceCard;
    }


    public abstract void cardAction(Player player);

    public String getCardName() {
        return cardName;
    }
}
