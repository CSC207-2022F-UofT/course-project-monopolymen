package GameEntities.Cards;

import GameEntities.Player;

public class AdvanceCard extends Card{
    private int tileNumber;

    private String tileName; //This is more for the benefit of the coder so they know what tile the
    //tile number refers to. May Remove later.

    private boolean collectGo; //Some cards let you collect money when you pass go if you pass by go,
    //others don't.

    protected AdvanceCard(String cardName, String cardDisplayName, String flavourText,
                          boolean ownable, boolean chanceCard) {
        super(cardName, cardDisplayName, flavourText, ownable, chanceCard);
    }


    @Override
    public void cardAction(Player player) {
        int player_pos = player.getPosition();
        if(this.getCardName().equals("GoToJail")){
            player.enterJail();
        }
        else if (player_pos <= this.tileNumber){
            player.updatePosition(this.tileNumber - player_pos);
        }
        else if(player_pos > this.tileNumber && collectGo){
            player.updatePosition((39 - player_pos) + this.tileNumber);
        }
        else if(player_pos> this.tileNumber && !collectGo){
            player.updatePosition((39 - player_pos) + this.tileNumber);
            player.addMoney(-200);
        }

    }

}
