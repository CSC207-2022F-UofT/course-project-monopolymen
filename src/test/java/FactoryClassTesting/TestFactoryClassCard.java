package FactoryClassTesting;
import static org.junit.Assert.*;

import GameEntities.Board;
import GameEntities.Cards.*;
import GameEntities.Tiles.*;


import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class TestFactoryClassCard {
    @Test
    public void TestOutOfJailFreeCard() throws FileNotFoundException {
        //test the OutOfJailFreeCard method
        List<List<String>> cardLines = FactoryProperty.extractor("src/main/resources/Cards.csv");
        cardLines.remove(0);
        GetOutOfJailCard test = FactoryCard.OutOfJailFreeCard(cardLines.get(0));
        assertTrue(test.isChanceCard());
        assertEquals(test.getCardDescription(),
                "Get Out Of Jail Free.This card may be kept until needed or traded");
        assertEquals(test.getCardName(), "card_chance_1");
    }
    @Test
    public void TestRepairCard() throws FileNotFoundException {
        //test the RepairCard method
        List<List<String>> cardLines = FactoryProperty.extractor("src/main/resources/Cards.csv");
        cardLines.remove(0);
        PropertyRepairCard test = FactoryCard.repairCard(cardLines.get(1));
        assertTrue(test.isChanceCard());
        assertEquals(test.getCardName(), "card_chance_2");
        assertEquals(test.getCardDescription(),
                "Make general repairs on all your property: For each House pay $25 and For each Hotel pay $100");
    }
    @Test
    public void testMoneyCard() throws FileNotFoundException {
        //test the MoneyCard method
        List<List<String>> cardLines = FactoryProperty.extractor("src/main/resources/Cards.csv");
        cardLines.remove(0);
        MoneyCard test = FactoryCard.moneyCard(cardLines.get(2));
        assertTrue(test.isChanceCard());
        assertEquals(test.getCardName(), "card_chance_3");
        assertEquals(test.getCardDescription(),
                "Bank pays your dividend of $50");
    }
    @Test
    public void testMoveBackCard() throws FileNotFoundException {
        //test the MoveBackCard
        List<List<String>> cardLines = FactoryProperty.extractor("src/main/resources/Cards.csv");
        cardLines.remove(0);
        AdvanceCard test = FactoryCard.moveBackCard(cardLines.get(11));
        assertTrue(test.isChanceCard());
        assertEquals(test.getCardName(), "card_chance_12");
        assertEquals(test.getCardDescription(),
                "Go back three spaces");
    }
    @Test
    public void testCommunityCard() throws FileNotFoundException {
        List<List<String>> cardLines = FactoryProperty.extractor("src/main/resources/Cards.csv");
        cardLines.remove(0);
        MoneyCard test = FactoryCard.moneyCard(cardLines.get(31));
        assertFalse(test.isChanceCard());
    }
    @Test
    public void testGetCards(){

    }
}
