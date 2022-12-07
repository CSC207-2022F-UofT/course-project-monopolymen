package game_entities;

import static org.junit.Assert.*;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.RailroadTile;
import game_entities.tiles.Tile;
import org.junit.Test;

import java.util.ArrayList;

public class TestPlayerClass {
    ArrayList<Tile> temp1 = new ArrayList<Tile>();
    Board board = new Board(temp1);
    Player testPlayer = new Player("Dummy", "dog", 1500, board);
    int[] rentDummy = {100,200,300};
    RailroadTile testProperty = new RailroadTile("test", "test", 100, rentDummy, 100,200);
    @Test
    public void LastRoll(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(6,3);
        int[] expected = {6,3};
        assertEquals(testPlayer.getLastRoll()[0], 6);
        assertEquals(testPlayer.getLastRoll()[1], 3);
    }
    @Test
    public void LastRoll1(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(1,4);
        int[] expected = {1,4};
        assertEquals(testPlayer.getLastRoll()[0], 1);
        assertEquals(testPlayer.getLastRoll()[1], 4);
    }
    @Test
    public void LastRoll2(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(2,2);
        int[] expected = {2,2};
        assertEquals(testPlayer.getLastRoll()[0], 2);
        assertEquals(testPlayer.getLastRoll()[1], 2);
    }
    @Test
    public void LastRoll3(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(5,3);
        int[] expected = {5,3};
        assertEquals(testPlayer.getLastRoll()[0], 5);
        assertEquals(testPlayer.getLastRoll()[1], 3);
    }
    @Test
    public void LastRoll4(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(6,1);
        int[] expected = {6,1};
        assertEquals(testPlayer.getLastRoll()[0], 6);
        assertEquals(testPlayer.getLastRoll()[1], 1);
    }
    @Test
    public void LastRoll5(){
        //test the setters and getters for the LastRoll
        testPlayer.setLastRoll(3,3);
        int[] expected = {3,3};
        assertEquals(testPlayer.getLastRoll()[0], 3);
        assertEquals(testPlayer.getLastRoll()[1], 3);
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
    public void moneySubtract(){
        //test what happens when the player subtracts a certain amount of money that exceeds the current money owned
        testPlayer.addMoney(100);
        assertEquals(testPlayer.getMoney(), 1600);
        testPlayer.subtractMoney(1700);
        assertEquals(testPlayer.getMoney(), -100);
    }
    @Test
    public void jail(){
        //test the methods dealing with the player and jail
        testPlayer.addTurnInJail();
        assertEquals(testPlayer.getTurnsInJail(), 0);
        testPlayer.resetTurnInJail();
        assertEquals(testPlayer.getTurnsInJail(), -1);
        testPlayer.addGetOutOfJailCard();
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
        testPlayer.resetConsecutiveDoubles();
        assertEquals(testPlayer.getConsecutiveDoubles(), 0);
    }
}
