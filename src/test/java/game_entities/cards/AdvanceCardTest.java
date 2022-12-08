package game_entities.cards;

import game_entities.Board;
import game_entities.Player;
import game_entities.TestObjects;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class AdvanceCardTest {
    /* There is only one test here because MovePlayerUseCase is what moves the player in the other
    AdvanceCard types, so testing will have to be done through that class.
     */
    @Test
    public void cardAction() throws FileNotFoundException {
        Board testBoard = TestObjects.testBoardReal();
        AdvanceCard goToJailCard = new AdvanceCard("", "", "",
                true, -1, "JailTile", testBoard);
        Player testPlayer1 = TestObjects.testPlayerRB();
        goToJailCard.action(testPlayer1);
        assertEquals(testBoard.getJailTilePosition(), testPlayer1.getPosition());



    }


}