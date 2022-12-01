package game_entities;

import game_entities.cards.*;
import game_entities.tiles.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/** The goal of this class is to create a Board.
 *  There are 4 required CSV files to create a board,
 *  1) ColoredProperty CSV
 *  2) RailRoadProperty CSV
 *  3) UtilityProperty CSV
 *  4) Community/Chance Card CSV
 */
public class FactoryBoard {
    /** Take in 4 CSV and create a board.
     *
     * @param colorPropertyCSV      A String Representing the path of the CSV file containing information about
     *                              ColorProperties
     * @param utilityPropertyCSV    A String Representing the path of the CSV file containing information about
     *                              UtilityProperties
     * @param railRoadPropertyCSV   A String Representing the path of the CSV file containing information about
     *                              RailRoadProperties
     * @param cardCSV               A String Representing the path of the CSV file containing information about cards
     * @return                      Return a board that is made from the given CSV files
     */
    public static Board boardMaker(String colorPropertyCSV, String utilityPropertyCSV, String railRoadPropertyCSV,
                            String cardCSV) throws FileNotFoundException {
        List<ColorPropertyTile> colorProperties = FactoryProperty.initializeColorProperties(colorPropertyCSV);
        List<UtilityTile> utilityProperties = FactoryProperty.initializeUtilityProperties(utilityPropertyCSV);
        List<RailroadTile> railRoadProperties = FactoryProperty.initializeRailRoadProperties(railRoadPropertyCSV);
        List<Tile> orderedTileList = order(colorProperties, railRoadProperties, utilityProperties);
        ArrayList<Tile> orderTileArrayList = new ArrayList<Tile>(orderedTileList);
        Board board = new Board(orderTileArrayList);
        ArrayList<Card>[] cards = FactoryCard.getCards(cardCSV, board);
        cardUpdate(cards, board);
        return board;
    }

    /** Take in a list of properties and use that to create an ordered ArrayList of tiles, which will include TaxTiles,
     * GoToJailTile, FreeParkingTile, GoTile, JailTil, Chance tiles, Community tiles
     *
     * @param colorProperty     An ArrayList of colorProperties that will be on the board
     * @param railRoad          An ArrayList of railRoad property that will be on the board
     * @param utility           An ArrayList of utility property that will be on the board
     * @return                  Return an ordered ArrayList of tiles that represents the tiles on the board
     */
    public static ArrayList<Tile> order(List<ColorPropertyTile> colorProperty, List<RailroadTile> railRoad,
                                        List<UtilityTile> utility){
        ArrayList<Tile> tileList = new ArrayList<Tile>();
        tileList.add(new GoTile());
        tileList.add(colorProperty.get(0));
        tileList.add(new DrawCardTile
                ("Community Chest Tile", "Community Chest Tile", false));
        tileList.add(colorProperty.get(1));
        tileList.add(new TaxTile("Income Tax", "Income Tax", 200));
        tileList.add(railRoad.get(0));
        tileList.add(colorProperty.get(2));
        tileList.add(new DrawCardTile
                ("Chance Tile", "Chance Tile", true));
        tileList.add(colorProperty.get(3));
        tileList.add(colorProperty.get(4));
        tileList.add(new JailTile());
        tileList.add(colorProperty.get(5));
        tileList.add(utility.get(0));
        tileList.add(colorProperty.get(6));
        tileList.add(colorProperty.get(7));
        tileList.add(railRoad.get(1));
        tileList.add(colorProperty.get(8));
        tileList.add(new DrawCardTile
                ("Community Chest Tile", "Community Chest Tile", false));
        tileList.add(colorProperty.get(9));
        tileList.add(colorProperty.get(10));
        tileList.add(new FreeParkingTile());
        tileList.add(colorProperty.get(11));
        tileList.add(new DrawCardTile
                ("Chance Tile", "Chance Tile", true));
        tileList.add(colorProperty.get(12));
        tileList.add(colorProperty.get(13));
        tileList.add(railRoad.get(2));
        tileList.add(colorProperty.get(14));
        tileList.add(colorProperty.get(15));
        tileList.add(utility.get(1));
        tileList.add(colorProperty.get(16));
        tileList.add(new GoToJailTile());
        tileList.add(colorProperty.get(17));
        tileList.add(colorProperty.get(18));
        tileList.add(new DrawCardTile
                ("Community Chest Tile", "Community Chest Tile", false));
        tileList.add(colorProperty.get(19));
        tileList.add(railRoad.get(3));
        tileList.add(new DrawCardTile
                ("Chance Tile", "Chance Tile", true));
        tileList.add(colorProperty.get(20));
        tileList.add(new TaxTile("Luxury Tile", "Luxury Tile", 200));
        tileList.add(colorProperty.get(21));
        return tileList;
    }

    /** Take in  an ArrayList of cards and add them to the board
     *
     * @param cards     The ArrayList of cards that we are trying to add
     * @param board     The board that we are trying to add the cards to
     */
    public static void cardUpdate(ArrayList<Card>[] cards, Board board) {
        for (Card chanceCard : cards[0]){
            board.addChanceCard(chanceCard);
        }
        for (Card communityCard : cards[1]){
            board.addCommunityCard(communityCard);
        }
    }
}
