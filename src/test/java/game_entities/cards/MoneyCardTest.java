package game_entities.cards;


import game_entities.Player;
import game_entities.TestObjects;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MoneyCardTest {

    @Test
    public void testAction() {
        Player testPlayer1 = TestObjects.testPlayerFB();
        MoneyCard testCard1 = TestObjects.testMoneyCard(-200);
        testCard1.action(testPlayer1);
        assertEquals(1300, testPlayer1.getMoney());

        Player testPlayer2 = TestObjects.testPlayerFB();
        MoneyCard testCard2 = TestObjects.testMoneyCard(250);
        testCard2.action(testPlayer2);
        assertEquals(1750, testPlayer2.getMoney());

        Player testPlayer3 = TestObjects.testPlayerFB();
        MoneyCard testCard3 = TestObjects.testMoneyCard(0);
        testCard3.action(testPlayer3);
        assertEquals(1500, testPlayer3.getMoney());

    }

}

