package TurnUseCases.TradeUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import java.util.Arrays;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TradeOptionTest {

    Player player1 = new Player("player1", "player1", 1500);

    ColorPropertyTile fakeStreet = new ColorPropertyTile("red", "fakeStreet",
            "Fake Street", 500, 100, 0, 0,
            0, 0, 0, 0, 0, 0, 0);


    Player player2 = new Player("player2", "player2", 1500);

    ColorPropertyTile otherRoad = new ColorPropertyTile("blue", "otherRoad",
            "Other Road", 500, 100, 0, 0,
            0, 0, 0, 0, 0, 0, 0);

    ArrayList<Property> p1Properties = new ArrayList<>();
    ArrayList<Property> p2Properties = new ArrayList<>();


    @Before
    public void setUp() {
        player1.addProperty(fakeStreet);
        player2.addProperty(otherRoad);
        p1Properties.add(fakeStreet);
        p2Properties.add(otherRoad);

    }

    @After
    public void tearDown() {
        player1.sellProperty(fakeStreet);
        player2.sellProperty(otherRoad);
        p1Properties.remove(fakeStreet);
        p2Properties.remove(otherRoad);
    }

    @Test(timeout = 100)
    public void getPlayer1Money() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.getProperties(), player2.getProperties());

        assertEquals(1500, tradeOption.getPlayer1Money());
    }

    @Test
    public void getPlayer2Money() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.getProperties(), player2.getProperties());

        assertEquals(1500, tradeOption.getPlayer2Money());
    }

    @Test(timeout = 100)
    public void getPlayer1Properties() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.getProperties(), player2.getProperties());
        ArrayList<Property> actualProperties = tradeOption.getPlayer1Properties();
        assertEquals(p1Properties.get(0), actualProperties.get(0));
    }

        @Test
        public void getPlayer2Properties () {
            TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                    player1.getProperties(), player2.getProperties());
            ArrayList<Property> actualProperties = tradeOption.getPlayer2Properties();
            assertEquals(p2Properties.get(0), actualProperties.get(0));



        }

    }
