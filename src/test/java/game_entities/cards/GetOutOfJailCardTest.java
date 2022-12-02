package game_entities.cards;

import game_entities.Player;
import game_entities.TestObjects;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GetOutOfJailCardTest {


    @Test
    public void action() {
        Player testPlayer = TestObjects.testPlayerFB();
        Card testCard = TestObjects.testGetOutOfJailCard();
        testCard.action(testPlayer);
        assertTrue(testPlayer.hasGetOutofJailFreeCard());

    }
}