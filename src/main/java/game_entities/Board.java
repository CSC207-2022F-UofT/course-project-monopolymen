package game_entities;

import game_entities.cards.Card;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.JailTile;
import game_entities.tiles.Property;
import game_entities.tiles.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public ArrayList<Card> getChanceCards() {
        return chanceCards;
    }

    public void addChanceCard(Card chanceCard){chanceCards.add(chanceCard);}


    public int getTilePosition(String tileName){
        for(int i = 0; i < tilesList.size(); i++){
            if(tileName.equals(tilesList.get(i).getTileName())){
                return i;
            }
        }
        return 0;
    }

    public List<Property> getPropertyTiles(){
        List<Property> propertyTiles = new ArrayList<>();
        for (Tile tile : tilesList) {
            if (tile instanceof Property) {
                propertyTiles.add((Property) tile);
            }
        }
        return propertyTiles;
    }

    public List<ColorPropertyTile> getColorPropertyTiles(){
        List<ColorPropertyTile> propertyTiles = new ArrayList<>();
        for (Tile tile : tilesList) {
            if (tile instanceof ColorPropertyTile) {
                propertyTiles.add((ColorPropertyTile) tile);
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
        Card returnCard;
        if(chanceCard){
            //chooses a card off the top
            returnCard = chanceCards.get(0);
            //moves the card to the back of the deck
            chanceCards.remove(0);
            chanceCards.add(returnCard);

        }

        else{
            returnCard = communityCards.get(0);
            communityCards.remove(0);
            communityCards.add(returnCard);
        }
        return returnCard;
    }

    public ArrayList<ColorPropertyTile> getSameColor(String color){
        ArrayList<ColorPropertyTile> sameColorList = new ArrayList<>();
        for(int i = 0; i < getColorPropertyTiles().size(); i++){
            ColorPropertyTile property = getColorPropertyTiles().get(i);
            if (property.getColor().equals(color)){
                sameColorList.add(property);
            }
        }
        return sameColorList;
    }

    public void shuffleCards(){
        Collections.shuffle(communityCards);
        Collections.shuffle(chanceCards);
    }

}
