package GameEntities;

import java.util.ArrayList;

public class Board {
    private  ArrayList<> communityCards = new ArrayList<>();
    private ArrayList<> chanceCards = new ArrayList<>();
    private ArrayList<Tiles> tilesList = new ArrayList<Tiles>();
    public Board(ArrayList communityCards, ArrayList chanceCards, ArrayList tilesList){

        this.communityCards = communityCards;
        this.chanceCards = chanceCards;
        this.tilesList = tilesList;
    }

    public int getTilePosition(String tileName){
        if (tileName.equals("JailTile")){
            return -1;
        }
        for(int i = 0; i <= tilesList.size(); i++){
            if(tileName.equals(tilesList.get(i).tileName)){
                return i;
            }
        }
        return 0;
    }
}
