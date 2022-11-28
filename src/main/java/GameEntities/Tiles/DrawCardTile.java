package GameEntities.Tiles;

import GameEntities.Cards.Card;
import GameEntities.Player;
import GameEntities.Board;


public class DrawCardTile extends Tile{

    private final boolean chanceTile;
    private final Board board;

    public DrawCardTile(String tileName, String tileDisplayName, boolean chanceTile, Board board) {
        super(tileName, tileDisplayName);
        this.chanceTile = chanceTile;
        this.board = board;
    }

    @Override
    public TileActionResultModel action(Player player) {
        Card drawnCard = board.pickCard(chanceTile);
        TileActionResultModel cardModel = drawnCard.action(player);
        return cardModel;
    }
}
