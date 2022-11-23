package GameEntities.Cards;
import GameEntities.Player;

public class GetOutOfJailCard  extends Card{
    public GetOutOfJailCard(String cardName, String cardDisplayName, String flavourText, boolean chanceCard) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
    }

    @Override
    public CardActionResultModel action(Player player) {
        player.addGetOutOfJailCard();
        return new CardActionResultModel(getCardDescription(), player, player.getPosition(), getCardName());
    }

}
