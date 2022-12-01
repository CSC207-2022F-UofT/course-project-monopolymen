package GameEntities.Tiles;
import GameEntities.Board;
import GameEntities.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestJailTile {
    Tile go = new GoTile();
    Tile jail = new JailTile();
    Tile goToJail = new GoToJailTile();
    ArrayList<Tile> temp1 = new ArrayList<Tile>(){{
        add(go);
        add(jail);
        add(goToJail);
    }};
    Board board = new Board(temp1);
    Player testPlayer = new Player("Dummy", "dog", 1500, board);

    @Test
    public void visiting() {
        // Test the player is just visiting if they activate the jail's action method
        assertEquals(testPlayer.getTurnsInJail(), -1);
        assertEquals("You are just visiting", jail.action(testPlayer, board).getFlavorText());
    }

    @Test
    public void inJail() {
        // Test the player is in jail if they land on "Go To Jail"
        assertEquals(testPlayer.getTurnsInJail(), -1);
        assertEquals(goToJail.action(testPlayer, board).getFlavorText(), "You are being sent to jail.");
        assertEquals(testPlayer.getTurnsInJail(), 0);
        assertEquals(testPlayer.getPosition(), board.getJailTilePosition());
    }

}
