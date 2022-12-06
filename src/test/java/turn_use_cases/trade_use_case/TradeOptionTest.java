package turn_use_cases.trade_use_case;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TradeOptionTest {
    ArrayList<Tile> temp1 = new ArrayList<Tile>();
    Board board = new Board(temp1);
    Player player1 = new Player("player1", "player1", 1500, board);

    int[] fakeRent = {};

    ColorPropertyTile fakeStreet = new ColorPropertyTile("red", "fakeStreet",
            "Fake Street", 500, fakeRent, 0, 0, 0);


    Player player2 = new Player("player2", "player2", 1500, board);

    ColorPropertyTile otherRoad = new ColorPropertyTile("blue", "otherRoad",
            "Other Road", 500, fakeRent, 0, 0, 0);

    ArrayList<Property> p1Properties = new ArrayList<>();
    ArrayList<Property> p2Properties = new ArrayList<>();


    @Before
    public void setUp() {
        player1.addProperty(fakeStreet);
        player2.addProperty(otherRoad);
        p1Properties.add(fakeStreet);
        p2Properties.add(otherRoad);
        player1.addGetOutOfJailCard();
        player2.addGetOutOfJailCard();

    }

    @After
    public void tearDown() {
        player1.sellProperty(fakeStreet);
        player2.sellProperty(otherRoad);
        p1Properties.remove(fakeStreet);
        p2Properties.remove(otherRoad);
        player1.removeGetOutOfJailCard();
        player2.removeGetOutOfJailCard();
    }

    @Test(timeout = 100)
    public void getPlayer1Money() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                player1.getProperties(), player2.getProperties(), player1, player2);

        assertEquals(1500, tradeOption.getPlayer1Money());
    }

    @Test
    public void getPlayer2Money() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                player1.getProperties(), player2.getProperties(), player1, player2);

        assertEquals(1500, tradeOption.getPlayer2Money());
    }

    @Test(timeout = 100)
    public void getPlayer1Properties() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                player1.getProperties(), player2.getProperties(), player1, player2);
        ArrayList<Property> actualProperties = tradeOption.getPlayer1Properties();
        assertEquals(p1Properties.get(0), actualProperties.get(0));
    }

        @Test
        public void getPlayer2Properties () {
            TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                    player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                    player1.getProperties(), player2.getProperties(), player1, player2);
            ArrayList<Property> actualProperties = tradeOption.getPlayer2Properties();
            assertEquals(p2Properties.get(0), actualProperties.get(0));



        }

        @Test
        public void getPlayer1JailCard() {
            TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                    player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                    player1.getProperties(), player2.getProperties(), player1, player2);
            assertEquals(player1.hasGetOutofJailFreeCard(), tradeOption.getPlayer1JailCard());
        }

    @Test
    public void getPlayer2JailCard() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                player1.getProperties(), player2.getProperties(), player1, player2);
        assertEquals(player2.hasGetOutofJailFreeCard(), tradeOption.getPlayer2JailCard());
    }


    }
