package GameEntities.Cards;

import  GameEntities.Board;
import GameEntities.Player;
//import GameEntities.TurnUserCases.MovePlayerUseCase;

public class AdvanceCard extends Card{
    private int tileNumber;

    private String tileName; //This is more for the benefit of the coder so they know what tile the
    //tile number refers to. May Remove later.

    private boolean collectGo; //Some cards let you collect money when you pass go if you pass by go,
    //others don't.

    public AdvanceCard(String cardName, String cardDisplayName, String flavourText,
                          boolean ownable, boolean chanceCard) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
    }


    public void cardAction(Player player) {
        /**
        int player_pos = player.getPosition();
        if(this.getCardName().equals("GoToJail")){
            player.enterJail();
        }
        else{
            MovePlayerUseCase movePlayer = new MovePlayerUseCase();
            movePlayer.startAction()

        }
         */

    }


}
