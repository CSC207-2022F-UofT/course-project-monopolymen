package GameEntities.Tiles;

import GameEntities.Cards.Card;
import GameEntities.Player;
import GameEntities.Board;

import java.util.ArrayList;


public class DrawCardTile extends Tile{

    private final boolean chanceTile;
    private Board board;

    public DrawCardTile(String tileName, String tileDisplayName, boolean chanceTile) {
        super(tileName, tileDisplayName);
        this.chanceTile = chanceTile;

        ArrayList<Tile> tileList  = new ArrayList<>();
        Board board = new Board(tileList);
        this.board = board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    @Override
    public TileActionResultModel action(Player player) {
        Card drawnCard = board.pickCard(chanceTile);
        TileActionResultModel cardModel = drawnCard.action(player);
        return cardModel;
    }
}
