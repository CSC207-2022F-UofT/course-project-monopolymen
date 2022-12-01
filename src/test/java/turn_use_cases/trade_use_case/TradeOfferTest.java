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

import static org.junit.Assert.*;

public class TradeOfferTest {
    ArrayList<Tile> temp1 = new ArrayList<Tile>();
    Board board = new Board(temp1);
    Player player1 = new Player("player1", "player1", 1500, board);

    int[] fakeRent = {};

    ColorPropertyTile fakeStreet = new ColorPropertyTile("red", "fakeStreet",
            "Fake Street", 500, fakeRent, 0, 0, 0);


    Player player2 = new Player("player2", "player2", 1500, board);

    ColorPropertyTile otherRoad = new ColorPropertyTile("blue", "otherRoad",
            "Other Road", 500, fakeRent, 0, 0, 0);

    @Before
    public void setUp() throws Exception {
        player1.addProperty(fakeStreet);
        player2.addProperty(otherRoad);
    }

    @After
    public void tearDown() throws Exception {
        player1.sellProperty(fakeStreet);
        player2.sellProperty(otherRoad);
    }

    @Test
    public void checkIsValidTrue() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);
        assertTrue(tradeOffer.isValid());
    }

    @Test
    public void checkIsValidFalseP1Money() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(2000, 0,
                propertiesOffered, propertiesReceived, player1, player2);
        assertFalse(tradeOffer.isValid());
    }

    @Test
    public void checkIsValidFalseP2Money() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(-2000, 0,
                propertiesOffered, propertiesReceived, player1, player2);
        assertFalse(tradeOffer.isValid());
    }

    @Test
    public void checkIsValidFalsePropertiesOffered() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(otherRoad);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);
        assertFalse(tradeOffer.isValid());
    }

    @Test
    public void checkIsValidFalsePropertiesReceived() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(fakeStreet);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);
        assertFalse(tradeOffer.isValid());
    }

    @Test
    public void checkIsValidFalseJailCard1() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(fakeStreet);
        TradeOffer tradeOffer = new TradeOffer(100, 1,
                propertiesOffered, propertiesReceived, player1, player2);
        assertFalse(tradeOffer.isValid());
    }

    @Test
    public void checkIsValidFalseJailCard2() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(fakeStreet);
        TradeOffer tradeOffer = new TradeOffer(100, -1,
                propertiesOffered, propertiesReceived, player1, player2);
        assertFalse(tradeOffer.isValid());
    }



    @Test
    public void getTradeMoney() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        assertEquals(100, tradeOffer.getTradeMoney());

    }

    @Test
    public void getPlayer1() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        assertEquals(player1, tradeOffer.getPlayer1());
    }

    @Test
    public void getPlayer2() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        assertEquals(player2, tradeOffer.getPlayer2());
    }

    @Test
    public void getPropertiesOffered() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        assertEquals(propertiesOffered, tradeOffer.getPropertiesOffered());
    }

    @Test
    public void getPropertiesReceived() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        assertEquals(propertiesReceived, tradeOffer.getPropertiesReceived());
    }

    @Test
    public void getJailCard() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        assertEquals(0, tradeOffer.getJailCard());
    }


}