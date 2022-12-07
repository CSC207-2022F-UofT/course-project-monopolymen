package game_entities.cards;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.Tile;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestMoneyCard {
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);
    MoneyCard moneyCard = new MoneyCard("MoneyCard", "Money Card", "Card",
            true, 600);
    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        CardActionResultModel test = moneyCard.action(player1);
        assertEquals(test.getFlavorText(), moneyCard.getCardDescription());
        assertEquals(test.getPlayerPosition(), 0);
        assertEquals(test.getCardName(), "MoneyCard");
        assertEquals(test.getPlayer(), player1);
        assertEquals(test.isChance(), true);
        assertEquals(player1.getMoney(), 2100);
    }

}
