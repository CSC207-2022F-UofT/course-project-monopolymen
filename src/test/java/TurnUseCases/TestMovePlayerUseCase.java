package TurnUseCases;
import static org.junit.Assert.*;
import TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;
// Need import here for implemented version of movePlayerOutputBoundary which is in presenter
import GameEntities.Board;
import org.junit.Test;

public class TestMovePlayerUseCase {
    @Test
    public void testUpdatePlayerNormal() {
        /**
         * Test if the player position updated after a normal move, meaning it doesn't land on "go to jail" tile
         */
        int[] playerRollAmount = {1, 2};
        Player player = new Player("player", "icon");
        MovePlayerPresenter movePlayerPresenter = new MovePlayerPresenter();
        Board board = new Board(); // Constructor will need inputs
        int currPlayerPosition = player.getPosition();
        int updatedPosition = currPlayerPosition + playerRollAmount[0] + playerRollAmount[1];
        MovePlayerUseCase movePlayerUseCase = new MovePlayerUseCase();
        movePlayerUseCase.startAction(playerRollAmount, player, movePlayerPresenter, board);
        assertEquals(updatedPosition, player.getPosition());
    }

    @Test
    public void testJailPlayerRollThreeTimes() {
        /**
         * Test if the player goes to jail if they rolled 3 times in a row
         */
        int[] playerRollAmount = {1, 1};
        Player player = new Player("player", "icon");
        MovePlayerPresenter movePlayerPresenter = new MovePlayerPresenter();
        Board board = new Board(); // Constructor will need inputs
        int jailTilePosition = board.getJailTilePosition();
        MovePlayerUseCase movePlayerUseCase = new MovePlayerUseCase();
        movePlayerUseCase.startAction(playerRollAmount, player, movePlayerPresenter, board);
        movePlayerUseCase.startAction(playerRollAmount, player, movePlayerPresenter, board);
        movePlayerUseCase.startAction(playerRollAmount, player, movePlayerPresenter, board);
        assertEquals(true, player.getTurnsInJail() != -1);
        assertEquals(jailTilePosition, player.getPosition());
    }

    @Test
    public void testLandOnGoToJailTile() {
        /**
         * Test if the player goes to jail if they hand on "go to jail" tile
         */
        int[] playerRollAmount = {6, 6};
        Player player = new Player("player", "icon");
        MovePlayerPresenter movePlayerPresenter = new MovePlayerPresenter();
        Board board = new Board(); // Constructor will need inputs
        int jailTilePosition = board.getJailTilePosition();
        MovePlayerUseCase movePlayerUseCase = new MovePlayerUseCase();
        movePlayerUseCase.startAction(playerRollAmount, player, movePlayerPresenter, board);
        //will need to find out how to land on "go to jail", this will probably take around 2 to 3 rolls
        assertEquals(true, player.getTurnsInJail() != -1);
        assertEquals(jailTilePosition, player.getPosition());
    }

}
