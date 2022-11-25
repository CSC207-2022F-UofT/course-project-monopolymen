package TurnUseCases.TradeUseCase;

import GameEntities.Board;
import GameEntities.Cards.Card;
import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;
import GameEntities.Tiles.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class TradeUseCaseTest {
    ArrayList<Card> temp = new ArrayList<Card>();
    ArrayList<Tile> temp1 = new ArrayList<Tile>();
    Board board = new Board(temp, temp, temp1);
    Player player1 = new Player("player1", "player1", 1500, board);

    Player player2 = new Player("player2", "player2", 1500, board);

    int[] fakeRent = {};

    ColorPropertyTile fakeStreet = new ColorPropertyTile("red", "fakeStreet",
            "Fake Street", 500, fakeRent, 0, 0, 0);


    ColorPropertyTile otherRoad = new ColorPropertyTile("blue", "otherRoad",
            "Other Road", 500, fakeRent, 0, 0, 0);

    TemporaryTurnActionPresenter presenter = new TemporaryTurnActionPresenter();
    TradeUseCase tradeUseCase = new TradeUseCase(presenter);

    @Before
    public void setUp() throws Exception {
        player1.addProperty(fakeStreet);
        player2.addProperty(otherRoad);
    }

    @After
    public void tearDown() throws Exception {
        player1.sellProperty(fakeStreet);
        player1.sellProperty(otherRoad);
        player2.sellProperty(otherRoad);

        if(player1.hasGetOutofJailFreeCard()){
            player1.removeGetOutOfJailCard();
        }

        if(player2.hasGetOutofJailFreeCard()){
            player2.removeGetOutOfJailCard();
        }

    }

    @Test
    public void choosePlayer() {
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);

        ArrayList<Player> potentialPlayers = new ArrayList<>();
        potentialPlayers.add(player2);


        ArrayList<Player> actual = tradeUseCase.choosePlayer(listOfPlayers, player1);
        assertEquals(potentialPlayers, actual);

    }

    @Test
    public void getTradeOptions() {
        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(),
                player1.hasGetOutofJailFreeCard(), player2.hasGetOutofJailFreeCard(),
                player1.getProperties(), player2.getProperties());

        TradeOption actual = tradeUseCase.getTradeOptions(player1, player2);

        assertEquals(tradeOption.getPlayer1Money(), actual.getPlayer1Money());
        assertEquals(tradeOption.getPlayer2Money(), actual.getPlayer2Money());
        assertEquals(tradeOption.getPlayer1Properties(), actual.getPlayer1Properties());
        assertEquals(tradeOption.getPlayer2Properties(), actual.getPlayer2Properties());

    }

    @Test
    public void makeOfferValid() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        tradeUseCase.makeOffer(tradeOffer, player1, player2);

    }

    @Test
    public void makeOfferNotValid() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(otherRoad);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(fakeStreet);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        tradeUseCase.makeOffer(tradeOffer, player1, player2);

    }

    @Test
    public void getResultOfTradeOffer1() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        tradeUseCase.getResultOfTradeOffer(1, player1, player2, tradeOffer);

    }

    @Test
    public void getResultOfTradeOffer2() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        tradeUseCase.getResultOfTradeOffer(2, player1, player2, tradeOffer);

    }

    @Test
    public void getResultOfTradeOffer3() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        tradeUseCase.getResultOfTradeOffer(3, player1, player2, tradeOffer);

    }

    @Test
    public void getResultOfTradeOffer4() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        tradeUseCase.getResultOfTradeOffer(4, player1, player2, tradeOffer);

    }

    @Test
    public void executeOffer() {
        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 0,
                propertiesOffered, propertiesReceived, player1, player2);

        ArrayList<Property> player1Properties = new ArrayList<>();
        player1Properties.add(otherRoad);
        ArrayList<Property> player2Properties = new ArrayList<>();
        player2Properties.add(fakeStreet);

        tradeUseCase.ExecuteOffer(player1, player2, tradeOffer);

        assertEquals(1600, player1.getMoney());
        assertEquals(1400, player2.getMoney());
        assertEquals(player1Properties, player1.getProperties());
        assertEquals(player2Properties, player2.getProperties());
        assertFalse(player1.hasGetOutofJailFreeCard());
        assertFalse(player2.hasGetOutofJailFreeCard());



    }

    @Test
    public void executeOfferJailCard1() {
        player1.addGetOutOfJailCard();

        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, 1,
                propertiesOffered, propertiesReceived, player1, player2);

        ArrayList<Property> player1Properties = new ArrayList<>();
        player1Properties.add(otherRoad);
        ArrayList<Property> player2Properties = new ArrayList<>();
        player2Properties.add(fakeStreet);

        tradeUseCase.ExecuteOffer(player1, player2, tradeOffer);

        assertEquals(1600, player1.getMoney());
        assertEquals(1400, player2.getMoney());
        assertEquals(player1Properties, player1.getProperties());
        assertEquals(player2Properties, player2.getProperties());
        assertFalse(player1.hasGetOutofJailFreeCard());
        assertTrue(player2.hasGetOutofJailFreeCard());



    }

    @Test
    public void executeOfferJailCard2() {
        player2.addGetOutOfJailCard();

        ArrayList<Property> propertiesOffered = new ArrayList<>();
        propertiesOffered.add(fakeStreet);
        ArrayList<Property> propertiesReceived = new ArrayList<>();
        propertiesReceived.add(otherRoad);
        TradeOffer tradeOffer = new TradeOffer(100, -1,
                propertiesOffered, propertiesReceived, player1, player2);

        ArrayList<Property> player1Properties = new ArrayList<>();
        player1Properties.add(otherRoad);
        ArrayList<Property> player2Properties = new ArrayList<>();
        player2Properties.add(fakeStreet);

        tradeUseCase.ExecuteOffer(player1, player2, tradeOffer);

        assertEquals(1600, player1.getMoney());
        assertEquals(1400, player2.getMoney());
        assertEquals(player1Properties, player1.getProperties());
        assertEquals(player2Properties, player2.getProperties());
        assertFalse(player2.hasGetOutofJailFreeCard());
        assertTrue(player1.hasGetOutofJailFreeCard());



    }
}