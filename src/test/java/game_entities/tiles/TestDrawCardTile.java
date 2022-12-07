package game_entities.tiles;
import game_entities.Board;
import game_entities.Player;
import game_entities.cards.Card;
import game_entities.cards.CardActionResultModel;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
public class TestDrawCardTile {
    DrawCardTile drawCard = new DrawCardTile("DrawCard", "Draw Card", false);
    Card card = new Card("CardName", "Card Name", "Card Works", false) {
        @Override
        public CardActionResultModel action(Player player) {
            return null;
        }
    };
    ArrayList<Tile> test = new ArrayList<Tile>(){{
        add(drawCard);
    }};
    Board board = new Board(test);

    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        board.addCommunityCard(card);
        drawCard.setBoard(board);
        TileActionResultModel test = drawCard.action(player1, board);
        assertEquals(test.getFlavorText(), card.getCardDescription());
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.getPlayerPosition(), player1.getPosition());
    }
}
