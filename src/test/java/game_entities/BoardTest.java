package game_entities;

import game_entities.cards.Card;
import game_entities.tiles.Tile;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testingFactory() throws FileNotFoundException {
        Board testBoard = TestObjects.testBoardReal();
        Tile goTile = testBoard.getTile(0);
        assertEquals("GoTile", goTile.getTileName());


    }

    @Test
    public void getTilePosition() throws FileNotFoundException {
        Board testBoard = TestObjects.testBoardReal();
        ArrayList<Tile> tileList =  testBoard.getTilesList();
        for(int i = 0; i < tileList.size(); i++){
            int tileNumber = testBoard.getTilePosition(tileList.get(i).getTileName());
            assertEquals(i, tileNumber);
        }

    }

    @Test
    public void getPropertyTiles() {
    }

    @Test
    public void getJailTilePosition() throws FileNotFoundException {
        Board testBoard = TestObjects.testBoardReal();
        int jailPosition = testBoard.getJailTilePosition();
        assertEquals(10, jailPosition);
    }

    @Test
    public void pickCard() throws FileNotFoundException {
        Board testBoard = TestObjects.testBoardReal();
        ArrayList<Card> chancesCards = testBoard.getChanceCards();
        ArrayList<Card> communityCards = testBoard.getCommunityCards();
        Card firstChanceCard = chancesCards.get(0);
        Card firstCommunityCard = communityCards.get(0);

        Card pickedChanceCard = testBoard.pickCard(true);
        Card pickedCommunityCard = testBoard.pickCard(false);
        //Checks whether the picked card is the first card in the card list and whether it moved the card to the back of the list
        assertEquals(firstChanceCard, pickedChanceCard);
        assertEquals(chancesCards.get(chancesCards.size() - 1), pickedChanceCard);
        assertEquals(firstCommunityCard, pickedCommunityCard);
        assertEquals(communityCards.get(communityCards.size() - 1), pickedCommunityCard);


    }
}