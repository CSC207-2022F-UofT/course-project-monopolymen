package game_entities;

import game_entities.cards.AdvanceCard;
import game_entities.cards.GetOutOfJailCard;
import game_entities.cards.MoneyCard;
import game_entities.tiles.Tile;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TestObjects {
    public static AdvanceCard testAdvanceCard(int testNumber, String testName){
        return new AdvanceCard("Test Card", "Test Card Display",
                "This is a test card", false, testNumber, testName, null);
    }

    public static MoneyCard testMoneyCard(int testNumber){
        return new MoneyCard("Test Card", "Test Card Display",
                "This is a test card", false, testNumber);
    }

    public static GetOutOfJailCard testGetOutOfJailCard(){
        return new GetOutOfJailCard("Test Card", "Test Card Display",
                "This is a test card", false);
    }

    public static Player testPlayerFB(){
        Board testBoard = testBoardFake();
        return new Player("Test Player", "Test Icon", 1500,
                testBoard);
    }

    public static Player testPlayerRB() throws FileNotFoundException {
        Board testBoard = testBoardReal();
        return new Player("Test Player", "Test Icon", 1500,
                testBoard);
    }

    public static Board testBoardFake(){
        ArrayList<Tile> emptyList = new ArrayList<>();
        return new Board(emptyList);
    }

    public static Board testBoardReal() throws FileNotFoundException {
        String cPropertiesCSV = "src/main/resources/Data/property_csvs/Color Properties Monopoly.csv";
        String sPropertiesCSV = "src/main/resources/Data/property_csvs/Station Properties Monopoly.csv";
        String uPropertiesCSV = "src/main/resources/Data/property_csvs/Utility Properties Monopoly.csv";
        String cardsCSV = "src/main/resources/cards.csv";
        FactoryBoard factoryBoard = new FactoryBoard();
        return FactoryBoard.boardMaker(cPropertiesCSV, uPropertiesCSV, sPropertiesCSV, cardsCSV);
    }
}
