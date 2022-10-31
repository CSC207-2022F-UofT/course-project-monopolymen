package GameEntities;

import java.util.ArrayList;

public class Board {
    private  ArrayList<> communityCards;
    private ArrayList<> chanceCards;
    private ArrayList<Tiles> tilesList;
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
