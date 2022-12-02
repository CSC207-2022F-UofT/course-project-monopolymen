package game_entities;

import game_entities.cards.AdvanceCard;
import game_entities.cards.GetOutOfJailCard;
import game_entities.cards.MoneyCard;
import game_entities.tiles.Tile;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TestObjects {
    public static AdvanceCard testAdvanceCard(int testNumber, String testName){
        AdvanceCard testCard = new AdvanceCard("Test Card", "Test Card Display",
                "This is a test card", false, testNumber, testName);
        return testCard;
    }

    public static MoneyCard testMoneyCard(int testNumber){
        MoneyCard testCard = new MoneyCard("Test Card", "Test Card Display",
                "This is a test card", false, testNumber);
        return testCard;
    }

    public static GetOutOfJailCard testGetOutOfJailCard(){
        GetOutOfJailCard testCard = new GetOutOfJailCard("Test Card", "Test Card Display",
                "This is a test card", false);
        return testCard;
    }

    public static Player testPlayerFB(){
        Board testBoard = testBoardFake();
        Player testPlayer = new Player("Test Player", "Test Icon", 1500,
                testBoard);
        return testPlayer;
    }

    public static Player testPlayerRB() throws FileNotFoundException {
        Board testBoard = testBoardReal();
        Player testPlayer = new Player("Test Player", "Test Icon", 1500,
                testBoard);
        return testPlayer;
    }

    public static Board testBoardFake(){
        ArrayList<Tile> emptyList = new ArrayList<>();
        Board testBoard = new Board(emptyList);
        return testBoard;
    }

    public static Board testBoardReal() throws FileNotFoundException {
        String cPropertiesCSV = "src/main/resources/Data/property_csvs/Color Properties Monopoly.csv";
        String sPropertiesCSV = "src/main/resources/Data/property_csvs/Station Properties Monopoly.csv";
        String uPropertiesCSV = "src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv";
        String cardsCSV = "src/main/resources/Cards.csv";
        FactoryBoard factoryBoard = new FactoryBoard();
        Board testBoard = factoryBoard.boardMaker(cPropertiesCSV, uPropertiesCSV, sPropertiesCSV, cardsCSV);
        return testBoard;
    }
}
