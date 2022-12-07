package game_entities.cards;
import game_entities.Board;
import game_entities.Player;
import game_entities.cards.*;
import game_entities.FactoryBoard;
import game_entities.tiles.*;


import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals(test.isChance(), true);
        assertEquals(player1.numGetOutofJailFreeCards(), 1);
    }
}
