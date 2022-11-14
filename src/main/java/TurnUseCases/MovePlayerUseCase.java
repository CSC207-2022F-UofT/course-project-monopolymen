package TurnUseCases;
import GameEntities.Player;
import GameEntities.Tiles.TileActionResultModel;
// Need import here for implemented version of movePlayerOutputBoundary which is in presenter

public class MovePlayerUseCase implements MovePlayerInputBoundary {
    /**
     *
     * @param playerRollAmount Int array of length 2 representing the roll of two die
     * @param player The player object that the action is being performed on
     * @param movePlayerPresenter Implemented movePlayerOutputBoundary to handle the connection to the turn presenter
     * @param board The board the game is operating on
     */
    public void makePlayerChoice(int[] playerRollAmount, Player player, MovePlayerUseCase movePlayerUseCase,
                                 MovePlayerPresenter movePlayerPresenter) {
        startAction(playerRollAmount, player, movePlayerPresenter, board);
    }

    public void startAction(int[] playerRollAmount, Player player, MovePlayerPresenter movePlayerPresenter,
                            Board board) {
        int rollSum = playerRollAmount[0] + playerRollAmount[1];
        boolean doubleRoll = playerRollAmount[0] == playerRollAmount[1];
        // Check if the player is in jail will be handled separately
        player.updateConsecutiveDoubles(doubleRoll);
        player.setLastRoll(playerRollAmount[0], playerRollAmount[1]);
        if (player.getConsecutiveDoubles() == 3) {
            player.enterJail();
            movePlayerPresenter.showChoices(new String[0], true);
        } else {
            player.updatePosition(rollSum);
            TileActionResultModel result = board.getTile(player.getPosition()).action();
            if (result.getMoveToPosition() != -2) {
                // Player landed on "go to jail"
                player.enterJail();
                movePlayerPresenter.showChoices(result.getChoices(), true);
                //TileActionResultModel will need another
                // instance variable to show what options the player can make
            } else {
                // Normal move
                movePlayerPresenter.showChoices(result.getChoices(), false);
            }
        }
    }
}
