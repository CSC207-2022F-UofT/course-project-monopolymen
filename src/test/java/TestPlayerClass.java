import static org.junit.Assert.*;
import GameEntities.Player;
import GameEntities.Tiles.RailroadTile;
import org.junit.Test;
public class TestPlayerClass {
    Player testPlayer = new Player("Dummy", "dog", 1500);
    int[] rentDummy = {100,200,300};
    RailroadTile testProperty = new RailroadTile("test", "test", 100, rentDummy, 100,200);
    @Test
    public void LastRoll(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(6,3);
        int[] expected = {6,3};
        assertEquals(testPlayer.getLastRoll(), expected);
    }
    @Test
    public void properties(){
        //test the methods that are related to the players owned property ArrayList
        testPlayer.addProperty(testProperty);
        boolean owns = testPlayer.ownsProperty(testProperty);
        assertTrue(owns);
        testPlayer.sellProperty(testProperty);
        owns = testPlayer.ownsProperty(testProperty);
        assertFalse(owns);
    }
    @Test
    public void money(){
        //test the getters and setters for money to make sure they function as expected
        testPlayer.addMoney(100);
        assertEquals(testPlayer.getMoney(), 1600);
        testPlayer.subtractMoney(100);
        assertEquals(testPlayer.getMoney(), 1500);
    }
    @Test
    public void jail(){
        //test the methods dealing with the player and jail
        testPlayer.addTurnInJail();
        assertEquals(testPlayer.getTurnsInJail(), 0);
        testPlayer.resetTurnInJail();
        assertEquals(testPlayer.getTurnsInJail(), -1);
        testPlayer.addTurnInJail();
        assertTrue(testPlayer.hasGetOutofJailFreeCard());
        testPlayer.removeGetOutOfJailCard();
        assertFalse(testPlayer.hasGetOutofJailFreeCard());
    }
    @Test
    public void consecutiveDoubles(){
        testPlayer.updateConsecutiveDoubles(5, 3);
        assertEquals(testPlayer.getConsecutiveDoubles(), 0);
        testPlayer.updateConsecutiveDoubles(5, 5);
        testPlayer.updateConsecutiveDoubles(5, 5);
        assertEquals(testPlayer.getConsecutiveDoubles(), 2);
        testPlayer.updateConsecutiveDoubles(5, 3);
        assertEquals(testPlayer.getConsecutiveDoubles(), 0);
        testPlayer.updateConsecutiveDoubles(5, 5);
        testPlayer.updateConsecutiveDoubles(5, 5);
        testPlayer.resetConesecutiveDoubles();
        assertEquals(testPlayer.getConsecutiveDoubles(), 0);
    }
}
