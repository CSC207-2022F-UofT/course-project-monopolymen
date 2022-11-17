package GameEntities.Cards;
import GameEntities.Player;

public class GetOutOfJailCard  extends Card{
    protected GetOutOfJailCard(String cardName, String cardDisplayName, String flavourText, boolean ownable, boolean chanceCard) {
        super(cardName, cardDisplayName, flavourText, ownable, chanceCard);
    }

    @Override
    public void cardAction(Player player) {
        player.enterJail();
        player.removeGetOutOfJailCard();

    }
}