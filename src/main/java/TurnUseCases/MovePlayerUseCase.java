package TurnUseCases;
import GameEntities.Player;
import GameEntities.Tiles.TileActionResultModel;

public class MovePlayerUseCase {
    /**
     *
     * @param playerRollAmount Int array of length 2 representing the roll of two die
     * @param player The player object that the action is being performed on
     * @param movePlayerOutputBoundary movePlayer output boundary to handle the connection to the turn presenter
     * @param board board the game is operating on
     */
    public void startAction(int[] playerRollAmount, Player player, MovePlayerOutputBoundary movePlayerOutputBoundary,
                            Board board) {
        int rollSum = playerRollAmount[0] + playerRollAmount[1];
        boolean doubleRoll = playerRollAmount[0] == playerRollAmount[1];
        // Check if the player is in jail will be handled separately
        player.updateConsecutiveDoubles(doubleRoll);
        player.setLastRoll(playerRollAmount[0], playerRollAmount[1]);
        if (player.getConsecutiveDoubles() == 3) {
            player.updatePosition(-1);//make issue about player class to update player to jail
        } else {
            player.updatePosition(rollSum);
            TileActionResultModel result = board.getTile(player.getPosition()).action();
            // way to know what tile they are on
            if (result.getMoveToPosition() != -2) {
                // Player landed on go to jail
                player.updatePosition(-1);//make issue about player class to update player to jail
                movePlayerOutputBoundary.showChoices(result.getChoices()); //TileActionResultModel will need another
                // instance variable to show what options the player can make
            } else {
                // Normal move
                movePlayerOutputBoundary.showChoices(result.getChoices());
            }
        }
    }
}
