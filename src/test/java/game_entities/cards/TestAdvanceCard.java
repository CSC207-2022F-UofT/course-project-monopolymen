package game_entities.cards;
import static org.junit.Assert.*;

import game_entities.Board;
import game_entities.Player;
import game_entities.cards.*;
import game_entities.FactoryBoard;
import game_entities.tiles.*;


import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TestAdvanceCard {
    AdvanceCard advanceCard = new AdvanceCard("AdvanceCard", "Advance Card", "Hello",
            true, -1, "GoToJail", null);
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);

    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        CardActionResultModel test = advanceCard.action(player1);
        assertEquals(test.getFlavorText(), advanceCard.getCardDescription());
        assertEquals(test.getPlayerPosition(), -1);
        assertEquals(test.getCardName(), "AdvanceCard");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.isChance(), true);
    }

}
