package game_entities.cards;

import game_entities.Player;
import game_entities.tiles.TileActionResultModel;

public class CardActionResultModel extends TileActionResultModel{

    private final String cardName;

    private final boolean isChance;

    /**
     * Construct an object that contains data for the result of a game tile's action
     *
     * @param flavorText     the text describing the action's result
     * @param player         the player that the action is being performed on
     * @param playerPosition the integer describing the current position of the player after the action
     */
    public CardActionResultModel(String flavorText, Player player, int playerPosition, String cardName,
                                 boolean isChance) {
        super(flavorText, player, playerPosition);
        this.cardName = cardName;
        this.isChance = isChance;
    }

    public String getCardName() {
        return cardName;
    }

    public boolean isChance() {
        return isChance;
    }
}
