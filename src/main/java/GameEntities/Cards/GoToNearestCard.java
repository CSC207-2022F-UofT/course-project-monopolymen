package GameEntities.Cards;

import GameEntities.Player;
import GameEntities.Tiles.Tile;
import GameEntities.Board;
import GameEntities.Tiles.UtilityTile;
import GameEntities.Tiles.RailroadTile;

public class GoToNearestCard extends Card{
    private final boolean isUtility;

    private final Board board;

    public GoToNearestCard(String cardName, String cardDisplayName, String flavourText, boolean chanceCard,
                           boolean isUtility, Board board) {
        super(cardName, cardDisplayName, flavourText, chanceCard);
        this.isUtility = isUtility;
        this.board = board;
    }

    @Override
    public CardActionResultModel action(Player player) {
        Tile tileSoFar;
        int position = player.getPosition();
        for(int i = 0; i < board.getTilesList().size(); i++){
            position = (position + 1) % board.getTilesList().size();
            tileSoFar = board.getTile(position);
            if((tileSoFar instanceof UtilityTile && isUtility) ||
                    (tileSoFar instanceof RailroadTile && !isUtility)){
                break;
            }
        }
        return new CardActionResultModel(getCardDescription(), player, position, getCardName(),
                isChanceCard());

    }
}

