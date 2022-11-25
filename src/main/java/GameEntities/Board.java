package GameEntities;

import java.util.ArrayList;

import GameEntities.Tiles.JailTile;
import GameEntities.Tiles.Tile;
import GameEntities.Tiles.Property;
import GameEntities.Cards.Card;

public class Board {
    private  ArrayList<Card> communityCards;
    private ArrayList<Card> chanceCards;
    private ArrayList<Tile> tilesList;
     public Board(ArrayList<Card> communityCards, ArrayList<Card> chanceCards, ArrayList<Tile> tilesList) {
        this.communityCards = communityCards;
        this.chanceCards = chanceCards;
        this.tilesList = tilesList;
    }


        public int getTilePosition(String tileName){
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

        public ArrayList<Tile> getTilesList() {
            return tilesList;
        }

        public Tile getTile(int tilePosition){
            return this.tilesList.get(tilePosition);
        }

        public int getJailTilePosition(){
            int jailTilePosition = 0;
            for(int i = 0; i < tilesList.size(); i++){
                if(tilesList.get(i) instanceof JailTile){
                    jailTilePosition = i;
                }
            }

            return jailTilePosition;
        }

    }
