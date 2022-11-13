package GameEntities;

import java.util.ArrayList;
import GameEntities.Tiles.Tile;
import GameEntities.Tiles.Property;

public class Board {
    private  ArrayList<> communityCards;
    private ArrayList<> chanceCards;
    private ArrayList<Tile> tilesList;
    public Board(ArrayList communityCards, ArrayList chanceCards, ArrayList tilesList){

        this.communityCards = communityCards;
        this.chanceCards = chanceCards;
        this.tilesList = tilesList;
    }

    public int getTilePosition(String tileName){
        if (tileName.equals("JailTile")){
            return -1;
        }
        for(int i = 0; i < tilesList.size(); i++){
            if(tileName.equals(tilesList.get(i).getTileName())){
                return i;
            }
        }
        return 0;
    }

    public ArrayList<Tile> getPropertyTiles(){
        ArrayList<Tile> propertyTiles = new ArrayList<Tile>();
        for(int i = 0; i < tilesList.size(); i++){
            if(tilesList.get(i) instanceof Property){
                propertyTiles.add(tilesList.get(i));
            }
        }
        return propertyTiles;
    }

    public Tile getTile(int tilePosition){
        if(tilePosition == -1){
            return this.tilesList.get(40);
        }
        else {
            return this.tilesList.get(tilePosition);
        }
    }

}
