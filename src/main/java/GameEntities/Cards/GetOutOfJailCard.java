package GameEntities.Cards;
import GameEntities.Player;

public class GetOutOfJailCard  extends Card{
    public GetOutOfJailCard(String cardName, String cardDisplayName, String flavourText, boolean chanceCard) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
    }

    @Override
    public void cardAction(Player player) {
        player.addGetOutOfJailCard();

    }
}
