package GameEntities;

import GameEntities.Cards.Card;
import GameEntities.Tiles.JailTile;
import GameEntities.Tiles.Property;
import GameEntities.Tiles.Tile;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private final ArrayList<Card> communityCards;
    private final ArrayList<Card> chanceCards;
    private final ArrayList<Tile> tilesList;

    public Board(ArrayList<Tile> tilesList) {
        ArrayList<Card> communityCards = new ArrayList<>();
        ArrayList<Card> chanceCards = new ArrayList<>();
        
        this.communityCards = communityCards;
        this.chanceCards = chanceCards;
        this.tilesList = tilesList;

    }

    public void addCommunityCard(Card communityCard){communityCards.add(communityCard);}
    public void addChanceCard(Card chanceCard){chanceCards.add(chanceCard);}


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

    public Card pickCard(boolean chanceCard){
        if(chanceCard){
            //chooses a card off the top
            Card returnCard = chanceCards.get(0);
            //moves the card to the back of the deck
            chanceCards.remove(0);
            chanceCards.add(returnCard);

            return returnCard;

        }

        else{
            Card returnCard = communityCards.get(0);
            communityCards.remove(0);
            communityCards.add(returnCard);
            return returnCard;
        }
    }

}
