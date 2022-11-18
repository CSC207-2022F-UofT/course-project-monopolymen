package TurnUseCases.TryToGetOutOfJailUseCase;
import GameEntities.Board;
import GameEntities.Player;
import GameEntities.Tiles.TileActionResultModel;
import GameEntities.Tiles.TilePassResultModel;
import TurnUseCases.MovePlayerUseCase.MovePlayerInputBoundary;
import TurnUseCases.MovePlayerUseCase.MovePlayerOutputBoundary;

import java.util.ArrayList;

/**
 * TryToGetOutOfJailUseCase (Class to handle the player's actions when they are in jail)
 * MovePlayerUseCase will handle the 3 cases: pay 50$, use "get out of jail" card, and roll for double
 * If the player rolls a double in jail, they will move the distance they rolled, but they cannot roll again
 */
public class TryToGetOutOfJailUseCase implements TryToGetOutOfJailInputBoundary {
    private TryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary;
    private Board board;
    /**
     * @param tryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary to handle the connection to \
     *                                        the turn presenter
     * @param board The board the game is operating on
     */
    public TryToGetOutOfJailUseCase(TryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary, Board board) {
        this.tryToGetOutOfJailOutputBoundary = tryToGetOutOfJailOutputBoundary;
        this.board = board;
    }

    @Override
    public void startAction(String playerOption, Player player) {
        if(playerOption.equals("Roll")) {

        }
    }

    @Override
    public ArrayList<String> getPlayerOptions(Player player) {
        ArrayList<String> playerOptions = new ArrayList<String>();
        playerOptions.add("Roll");
        if(player.getMoney() >= 50) {
            playerOptions.add("Pay");
        }
        if(player.getGetOutOfJailCard()) {
            playerOptions.add("Card");
        }
        return playerOptions;
    }
}
