package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;
import GameEntities.Tiles.TileActionResultModel;
import GameEntities.Board;
import GameEntities.Tiles.TilePassResultModel;
// Need import here for implemented version of movePlayerOutputBoundary which is in presenter

/**
 * MovePlayerUseCase (Class to handle moving the player and all its relevant logic such as passing a tile and landing
 * actions)
 * MovePlayerUseCase will handle the cases when the player rolls 3 consecutive doubles or land on "go to jail" and send
 * them to jail, otherwise, it will be a standard move with it moving the player to their final locations and calling
 * the tile's .action method
 */
public class MovePlayerUseCase implements MovePlayerInputBoundary {

    private MovePlayerOutputBoundary movePlayerOutputBoundary;
    private Board board;
    /**
     * @param movePlayerOutputBoundary movePlayerOutputBoundary to handle the connection to the turn presenter
     * @param board The board the game is operating on
     */
    public MovePlayerUseCase(MovePlayerOutputBoundary movePlayerOutputBoundary, Board board) {
        this.movePlayerOutputBoundary = movePlayerOutputBoundary;
        this.board = board;
    }

    @Override
    public void startAction(int[] playerRollAmount, Player player) {
        int rollSum = playerRollAmount[0] + playerRollAmount[1];
        boolean doubleRoll = playerRollAmount[0] == playerRollAmount[1];
        // Check if the player is in jail will be handled separately
        player.updateConsecutiveDoubles(playerRollAmount[0], playerRollAmount[1]);
        player.setLastRoll(playerRollAmount[0], playerRollAmount[1]);
        if (player.getConsecutiveDoubles() == 3) {
            player.enterJail();
            movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false, true);
        } else {
            // Presenter calls to show player passing through the tiles
            for(int i = 0; i < rollSum; i++) {
                player.updatePosition(1);
                TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
            }
            // Player passed through all the tiles and is now on their original position + rollSum
            TileActionResultModel result = board.getTile(player.getPosition()).action(player);
            if (result.getPlayerPosition() == -1) {
                // Player landed on "go to jail" and their position should now be in jail
                player.enterJail();
                movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false, true);
            } else {
                // Normal move
                movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), doubleRoll, false);
            }
        }
    }
}
