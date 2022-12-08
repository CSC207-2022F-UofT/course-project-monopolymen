package game_entities.cards;
import game_entities.Board;
import game_entities.tiles.FactoryProperty;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FactoryCard {
    /** Populate two ArrayLists with cards given a CSV file
     *
     * @param filePath      The path of the CSV file that we are trying to use to make our Card objects
     *
     * @param board         The board that the game is being played on, this is used to get the location of the tiles
     *
     * @return              Returns an array of ArrayLists, The first ArrayList includes all the chance cards and the
     *                      second ArrayList includes all the community cards
     */
    public static ArrayList<Card>[] getCards(String filePath, Board board) throws FileNotFoundException {
        ArrayList<Card> chance = new ArrayList<Card>();
        ArrayList<Card> community = new ArrayList<Card>();
        ArrayList<Card> [] combined = new ArrayList[2];
        List<List<String>> lines = FactoryProperty.extractor(filePath);
        lines.remove(0);
        for(List<String> line : lines){
            switch (line.get(0)) {
                case "jail":
                    Card jailC = OutOfJailFreeCard(line);
                    if (jailC.isChanceCard()) {
                        chance.add(jailC);
                    } else {
                        community.add(jailC);
                    }
                    break;
                case "repair":
                    Card repairC = repairCard(line);
                    if (repairC.isChanceCard()) {
                        chance.add(repairC);
                    } else {
                        community.add(repairC);
                    }
                    break;
                case "money":
                    Card moneyC = moneyCard(line);
                    if (moneyC.isChanceCard()) {
                        chance.add(moneyC);
                    } else {
                        community.add(moneyC);
                    }
                    break;
                case "advance":
                    Card advanceC = advanceCard(line, board);
                    if (advanceC.isChanceCard()) {
                        chance.add(advanceC);
                    } else {
                        community.add(advanceC);
                    }
                    break;
                case "advanceNear":
                    Card advanceNearC = advanceNearCard(line, board);
                    if (advanceNearC.isChanceCard()) {
                        chance.add(advanceNearC);
                    } else {
                        community.add(advanceNearC);
                    }
                    break;
                case "moveBack":
                    Card moveBackC = moveBackCard(line);
                    if (moveBackC.isChanceCard()) {
                        chance.add(moveBackC);
                    } else {
                        community.add(moveBackC);
                    }
                    break;
            }
        }
        combined[0] = chance;
        combined[1] = community;
        return combined;
    }

    /***
     * Create a jail Card
     *
     * @param information       A List including all the information about the card
     * @return                  Return a Card made from information
     */
    public static GetOutOfJailCard OutOfJailFreeCard(List<String> information){
        String cardName = information.get(1);
        String displayName = information.get(2);
        String text = information.get(3);
        boolean chanceCard = Objects.equals(information.get(4), "TRUE");
        return new GetOutOfJailCard(cardName, displayName, text, chanceCard);
    }

    /**
     * Create a repair Card
     *
     * @param information       A List including all the information about the card
     * @return                  Return a Card made from information
     */
    public static PropertyRepairCard repairCard(List<String> information){
        String cardName = information.get(1);
        String displayName = information.get(2);
        String text = information.get(3);
        boolean chanceCard = Objects.equals(information.get(4), "TRUE");
        int houseRepair = Integer.parseInt(information.get(8));
        int hotelRepair = Integer.parseInt(information.get(7));
        return new PropertyRepairCard(cardName, displayName, text, chanceCard, houseRepair,hotelRepair);
    }

    /**
     * Create a money Card
     *
     * @param information       A List including all the information about the card
     * @return                  Return a Card made from information
     */
    public static MoneyCard moneyCard(List<String> information){
        String cardName = information.get(1);
        String displayName = information.get(2);
        String text = information.get(3);
        boolean chanceCard = Objects.equals(information.get(4), "TRUE");
        int money = Integer.parseInt(information.get(6));
        return new MoneyCard(cardName, displayName, text, chanceCard, money);
    }

    /**
     * Create an advance Card
     *
     * @param information       A List including all the information about the card
     * @return                  Return a Card made from information
     */
    public static AdvanceCard advanceCard(List<String> information, Board board){
        String cardName = information.get(1);
        String displayName = information.get(2);
        String text = information.get(3);
        boolean chanceCard = Objects.equals(information.get(4), "TRUE");
        String tileName = information.get(5);
        int tileLocation = board.getTilePosition(tileName);
        return new AdvanceCard(cardName, displayName, text, chanceCard, tileLocation, tileName, board);
    }

    /**
     * Create an advanceNear Card
     *
     * @param information       A List including all the information about the card
     * @return                  Return a Card made from information
     */
    public static GoToNearestCard advanceNearCard(List<String> information, Board board){
        String cardName = information.get(1);
        String displayName = information.get(2);
        String text = information.get(3);
        boolean chanceCard = Objects.equals(information.get(4), "TRUE");
        boolean isUtility = Objects.equals(information.get(10), "Utility");
        return new GoToNearestCard(cardName, displayName, text, chanceCard, isUtility, board);
    }

    /**
     * Create a MoveBack Card
     *
     * @param information       A list including all the information about the card
     * @return                  Return a card made from information
     */
    public static AdvanceCard moveBackCard(List<String> information){
        String cardName = information.get(1);
        String displayName = information.get(2);
        String text = information.get(3);
        boolean chanceCard = Objects.equals(information.get(4), "TRUE");
        int move = Integer.parseInt(information.get(9));
        return new AdvanceCard(cardName, displayName, text, chanceCard, move, "N/A", null);
    }
}