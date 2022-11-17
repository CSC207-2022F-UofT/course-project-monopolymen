package TurnUseCases;
import GameEntities.Player;

public interface MovePlayerOutputBoundary {
    /**
     *
     * @param player The player object the action is being performed on
     * @param playerPosition The end position the player is supposed to be
     * @param rollAgain Boolean indicating if the player rolled a double and is able to roll again
     * @param turnOver Boolean indicating if the player is forced to end their turn, for example: if they land on
     *                 "go to jail" their turn will be forcefully ended instead if of any other move
     */
    public void showResultOfAction(Player player, int playerPosition, boolean rollAgain, boolean turnOver);

}
