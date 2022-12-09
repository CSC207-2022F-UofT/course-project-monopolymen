package turn_use_cases.move_player_use_case;
import game_entities.Player;
import game_entities.tiles.Tile;
import game_entities.tiles.TilePassResultModel;

/**
 * Output Boundary for MovePlayer UseCase, to be implemented by the Presenter
 */
public interface MovePlayerOutputBoundary {
    /**
     * @param player The player object the action is being performed on
     * @param playerPosition The end position the player is supposed to be
     * @param rollAgain Boolean indicating if the player rolled a double and is able to roll again
     * @param flavorText The string showing a description of the action
     * @param buttonText Text for the button depending on if they ca buy or not
     */
    void showResultOfAction(Player player, int playerPosition, boolean rollAgain, String flavorText,
                                   String buttonText);

    /**
     * Shows the result of player passing the tile
     * @param player The player object the action is being performed on
     * @param playerPosition The player's position
     * @param tilePassResultModel The data model of what happens when a player passes the tile
     */
    void showResultOfPass(Player player, int playerPosition, TilePassResultModel tilePassResultModel);

    /**
     * Shows the roll the player made
     * @param playerRollAmount The array of two elements representing the players rolls
     */
    void showRoll(int[] playerRollAmount);

    /**
     * Shows the card the player drew and if they moved or not
     * @param player The player object the action is being performed on
     * @param cardName Card's name
     * @param rollAgain If the player can roll again
     * @param isChance If the card is a chance or community card
     */
    void showCardDraw(Player player, String cardName, boolean rollAgain,
                             boolean isChance);

    /**
     * Shows that the property is purchasable
     * @param player The player object the action is being performed on
     * @param tile The tile the player landed on
     * @param buyable If the tile is buyable
     * @param rollAgain If the player rolled a double
     */
    void showBuyableProperty(Player player, Tile tile, boolean buyable, boolean rollAgain);
}
