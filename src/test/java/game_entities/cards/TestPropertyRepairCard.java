package game_entities.cards;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.Tile;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPropertyRepairCard {
    ArrayList<Tile> test = new ArrayList<>();
    Board board = new Board(test);
    PropertyRepairCard propertyRepairCard = new PropertyRepairCard("PropertyRepair",
            "Property Repair", "Test", true, 0, 0);
    @Test
    public void action() {
        Player player1 = new Player("Test", "test", 1500, board);
        CardActionResultModel test = propertyRepairCard.action(player1);
        assertEquals(test.getFlavorText(), propertyRepairCard.getCardDescription());
        assertEquals(test.getPlayerPosition(), 0);
        assertEquals(test.getCardName(), "PropertyRepair");
        assertEquals(test.getPlayer(), player1);
        assertTrue(test.isChance());
        assertEquals(player1.getMoney(),1500);

    }
}
