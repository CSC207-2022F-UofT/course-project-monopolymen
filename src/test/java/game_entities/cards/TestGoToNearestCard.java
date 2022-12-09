package game_entities.cards;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.Tile;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGoToNearestCard {
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);

    GoToNearestCard goToNearestCard = new GoToNearestCard("GoToNearest", "Go To Nearest", "Test", true, false, board);

    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        CardActionResultModel test = goToNearestCard.action(player1);
        assertEquals(test.getFlavorText(), goToNearestCard.getCardDescription());
        assertEquals(test.getPlayerPosition(), 0);
        assertEquals(test.getCardName(), "GoToNearest");
        assertEquals(test.getPlayer(), player1);
        assertTrue(test.isChance());
    }
}
