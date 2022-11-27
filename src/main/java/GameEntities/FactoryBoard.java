package GameEntities;

import GameEntities.Tiles.*;

import java.util.ArrayList;
import java.util.Objects;

/** The goal of this class is to create a Board.
 *  There are 4 required CSV files to create a board,
 *  1) ColoredProperty CSV
 *  2) RailRoadProperty CSV
 *  3) UtilityProperty CSV
 *  4) Community/Chance Card CSV
 */
public class FactoryBoard {
    /** Take in a list of properties and use that to create an ordered ArrayList of tiles, which will include TaxTiles,
     * GoToJailTile, FreeParkingTile, GoTile, JailTil, Chance Tiles, Community Tiles
     *
     * @param colorProperty     An ArrayList of colorProperties that will be on the board
     * @param railRoad          An ArrayList of railRoad property that will be on the board
     * @param utility           An ArrayList of utility property that will be on the board
     * @return                  Return an ordered ArrayList of tiles that represents the tiles on the board
     */
    public ArrayList<Tile> order(ArrayList<ColorPropertyTile> colorProperty, ArrayList<RailroadTile> railRoad,
                                 ArrayList<UtilityTile> utility){
        ArrayList<Tile> tileList = new ArrayList<Tile>();
        tileList.add(new GoTile());
        tileList.add(colorProperty.get(0));
        //Community Chest Tile
        tileList.add(colorProperty.get(1));
        tileList.add(new TaxTile("Income Tax", "Income Tax", 200));
        tileList.add(railRoad.get(0));
        tileList.add(colorProperty.get(2));
        //Chance Tile
        tileList.add(colorProperty.get(3));
        tileList.add(colorProperty.get(4));
        tileList.add(new JailTile());
        tileList.add(colorProperty.get(5));
        tileList.add(utility.get(0));
        tileList.add(colorProperty.get(6));
        tileList.add(colorProperty.get(7));
        tileList.add(railRoad.get(1));
        tileList.add(colorProperty.get(8));
        //Community Chest Tile
        tileList.add(colorProperty.get(9));
        tileList.add(colorProperty.get(10));
        tileList.add(new FreeParkingTile());
        tileList.add(colorProperty.get(11));
        //Chance Tile
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
        //Community Chest tile
        tileList.add(colorProperty.get(19));
        tileList.add(railRoad.get(3));
        //Chance Card tile
        tileList.add(colorProperty.get(20));
        tileList.add(new TaxTile("Luxury Tile", "Luxury Tile", 200));
        tileList.add(colorProperty.get(21));
        return tileList;
    }
}
