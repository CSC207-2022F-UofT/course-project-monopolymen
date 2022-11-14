package TurnUseCases;
import GameEntities.Player;

public interface MovePlayerInputBoundary {
    /**
     *
     * @param playerRollAmount Int array of length 2 representing the roll of two die
     * @param player The player object that the action is being performed on
     * @param movePlayerUseCase The UseCase class to handle all the logic
     * @param movePlayerPresenter Implemented movePlayerOutputBoundary to handle the connection to the turn presenter
     */
    public void makePlayerChoice(int[] playerRollAmount, Player player, MovePlayerUseCase movePlayerUseCase,
                                 MovePlayerPresenter movePlayerPresenter);
}
