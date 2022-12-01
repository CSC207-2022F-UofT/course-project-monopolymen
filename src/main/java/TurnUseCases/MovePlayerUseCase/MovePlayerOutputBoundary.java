package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;
import GameEntities.Tiles.Tile;
import GameEntities.Tiles.TilePassResultModel;

/**
 * Output Boundary for MovePlayer UseCase, to be implemented by the Presenter
 */
public interface MovePlayerOutputBoundary {
    /**
     * @param player The player object the action is being performed on
     * @param playerPosition The end position the player is supposed to be
     * @param rollAgain Boolean indicating if the player rolled a double and is able to roll again
     * @param flavorText The string showing a description of the action
     */
    public void showResultOfAction(Player player, int playerPosition, boolean rollAgain, String flavorText);

    /**
     * Shows the result of player passing the tile
     * @param player The player object the action is being performed on
     * @param playerPosition The player's position
     * @param tilePassResultModel The data model of what happens when a player passes the tile
     */
    public void showResultOfPass(Player player, int playerPosition, TilePassResultModel tilePassResultModel);

    /**
     * Shows the roll the player made
     * @param playerRollAmount The array of two elements representing the players rolls
     */
    public void showRoll(int[] playerRollAmount);

    /**
     * Shows that the player is able to buy the current property they are on
     * @param player The player object the action is being performed on
     * @param tile The tile that is available for purchase
     */
    public void showRoll(Player player, Tile tile);

    /**
     * Shows the card the player drew and if they moved or not
     * @param player The player object the action is being performed on
     * @param cardName Card's name
     * @param cardDescription Card's description
     * @param isChance If the card is a chance or community card
     */
    public void showCardDraw(Player player, String cardName, String cardDescription, boolean isChance);

    /**
     * Shows that the property is purchasable
     * @param player The player object the action is being performed on
     * @param tile The tile the player landed on
     * @param buyable If the tile is buyable
     */
    public void showBuyableProperty(Player player, Tile tile, boolean buyable);
}