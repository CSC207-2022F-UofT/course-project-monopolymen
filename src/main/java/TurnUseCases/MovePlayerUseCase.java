package TurnUseCases;
import GameEntities.Player;
import GameEntities.Tiles.TileActionResultModel;
import GameEntities.Board;
// Need import here for implemented version of movePlayerOutputBoundary which is in presenter

public class MovePlayerUseCase implements MovePlayerInputBoundary {
    /**
     *
     * @param playerRollAmount Int array of length 2 representing the roll of two die
     * @param player The player object that the action is being performed on
     * @param movePlayerPresenter Implemented movePlayerOutputBoundary to handle the connection to the turn presenter
     * @param board The board the game is operating on
     */
    public void makePlayerChoice(int[] playerRollAmount, Player player, MovePlayerPresenter movePlayerPresenter,
                                 Board board) {
        startAction(playerRollAmount, player, movePlayerPresenter, board);
    }

    public void startAction(int[] playerRollAmount, Player player, MovePlayerPresenter movePlayerPresenter,
                            Board board) {
        int rollSum = playerRollAmount[0] + playerRollAmount[1];
        boolean doubleRoll = playerRollAmount[0] == playerRollAmount[1];
        // Check if the player is in jail will be handled separately
        player.updateConsecutiveDoubles(playerRollAmount[0], playerRollAmount[1]);
        player.setLastRoll(playerRollAmount[0], playerRollAmount[1]);
        if (player.getConsecutiveDoubles() == 3) {
            player.enterJail();
            movePlayerPresenter.showResultOfAction(player, player.getPosition(), false, true);
        } else {
            player.updatePosition(rollSum);
            TileActionResultModel result = board.getTile(player.getPosition()).action(player);
            if (result.getPlayerPosition() == -1) {
                // Player landed on "go to jail" and their position should now be in jail
                player.enterJail();
                movePlayerPresenter.showResultOfAction(player, player.getPosition(), false, true);
            } else {
                // Normal move
                movePlayerPresenter.showResultOfAction(player, player.getPosition(), doubleRoll, false);
            }
        }
    }
}
