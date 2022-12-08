package game_entities.cards;

import game_entities.Board;
import game_entities.Player;
//import game_entities.TurnUserCases.move_player_use_case;

public class AdvanceCard extends Card{
    private int tileNumber;

    private String tileName; //This is more for the benefit of the coder so they know what tile the
    //tile number refers to. May Remove later.
    private Board board;

    public AdvanceCard(String cardName, String cardDisplayName, String flavourText,
                       boolean chanceCard, int tileNumber, String tileName,
                       Board board) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
        this.tileNumber = tileNumber;
        this.tileName = tileName;
        this.board = board;
    }

    @Override
    public CardActionResultModel action(Player player) {
        if(tileName.equals("Jail")){
            player.enterJail();
            return new CardActionResultModel(getCardDescription(), player, board.getJailTilePosition(), getCardName(),
                    isChanceCard());
        } else if (tileNumber == -3) {
            return new CardActionResultModel(getCardDescription(), player, player.getPosition() - tileNumber,
                    getCardName(), isChanceCard());
        }

        return new CardActionResultModel(getCardDescription(), player, tileNumber, getCardName(), isChanceCard());
    }


}
