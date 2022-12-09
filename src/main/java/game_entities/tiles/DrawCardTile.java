package game_entities.tiles;

import game_entities.cards.Card;
import game_entities.Player;
import game_entities.Board;
public class DrawCardTile extends Tile{

    private final boolean chanceTile;

    public DrawCardTile(String tileName, String tileDisplayName, boolean chanceTile) {
        super(tileName, tileDisplayName);
        this.chanceTile = chanceTile;
    }

    @Override
    public TileActionResultModel action(Player player, Board board) {
        Card drawnCard = board.pickCard(chanceTile);
        return drawnCard.action(player);
    }
}
