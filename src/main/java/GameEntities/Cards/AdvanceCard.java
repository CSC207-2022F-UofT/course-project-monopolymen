package GameEntities.Cards;

import  GameEntities.Board;
import GameEntities.Player;
//import GameEntities.TurnUserCases.MovePlayerUseCase;

public class AdvanceCard extends Card{
    private int tileNumber;

    private String tileName; //This is more for the benefit of the coder so they know what tile the
    //tile number refers to. May Remove later.

    public AdvanceCard(String cardName, String cardDisplayName, String flavourText,
                       boolean chanceCard, int tileNumber, String tileName) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
        this.tileNumber = tileNumber;
        this.tileName = tileName;
    }

    @Override
    public CardActionResultModel action(Player player) {
        if(tileName.equals("GoToJail")){
            player.enterJail();
            return new CardActionResultModel(getCardDescription(), player, -1, getCardName(),
                    isChanceCard());
        } else if (tileNumber == -3) {
            return new CardActionResultModel(getCardDescription(), player, player.getPosition() - tileNumber,
                    getCardName(), isChanceCard());
        }

        return new CardActionResultModel(getCardDescription(), player, tileNumber, getCardName(), isChanceCard());
    }


}
