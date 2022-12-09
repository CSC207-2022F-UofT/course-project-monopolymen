package game_entities.cards;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.Tile;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGetOutOfJailCard {
    GetOutOfJailCard getOutOfJailCard = new GetOutOfJailCard("JailCard", "Jail Card",
            "Hello", true);
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);

    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        CardActionResultModel test = getOutOfJailCard.action(player1);
        assertEquals(test.getFlavorText(), getOutOfJailCard.getCardDescription());
        assertEquals(test.getPlayerPosition(), 0);
        assertEquals(test.getCardName(), "JailCard");
        assertEquals(test.getPlayer(), player1);
        assertTrue(test.isChance());
        assertEquals(player1.numGetOutofJailFreeCards(), 1);
    }
}
