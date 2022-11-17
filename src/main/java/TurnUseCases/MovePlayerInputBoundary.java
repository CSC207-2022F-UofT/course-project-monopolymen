package TurnUseCases;
import GameEntities.Player;
import GameEntities.Board;

public interface MovePlayerInputBoundary {
    /**
     *
     * @param playerRollAmount Int array of length 2 representing the roll of two die
     * @param player The player object that the action is being performed on
     * @param movePlayerPresenter Implemented movePlayerOutputBoundary to handle the connection to the turn presenter
     * @param board The board the game is operating on
     */
    public void makePlayerChoice(int[] playerRollAmount, Player player, MovePlayerPresenter movePlayerPresenter,
                                 Board board);
}
