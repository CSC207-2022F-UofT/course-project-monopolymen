package turn_use_cases.try_to_get_out_of_jail_use_case;
import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.*;
import turn_use_cases.move_player_use_case.MovePlayerUseCase;
import turn_use_cases.end_turn_use_case.EndTurnUseCase;
import java.util.ArrayList;
import game_entities.cards.CardActionResultModel;

/**
 * try_to_get_out_of_jail_use_case (Class to handle the player's actions when they are in jail)
 * move_player_use_case will handle the 3 cases: pay 50$, use "get out of jail" card, and roll for double
 * If the player rolls a double in jail, they will move the distance they rolled, but they cannot roll again
 */
public class TryToGetOutOfJailUseCase implements TryToGetOutOfJailInputBoundary {
    private TryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary;
    private Board board;
    private EndTurnUseCase endTurnUseCase;
    private MovePlayerUseCase movePlayerUseCase;
    /**
     * @param tryToGetOutOfJailOutputBoundary TryToGetOutOfJailOutputBoundary to handle the display
     * @param board The board the game is operating on
     * @param endTurnUseCase Use Case to force end the player's turn if they are sent to jail or failed their roll
     * @param movePlayerUseCase Use Case to move the player if they get out of jail
     */
    public TryToGetOutOfJailUseCase(TryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary, Board board,
                                    EndTurnUseCase endTurnUseCase, MovePlayerUseCase movePlayerUseCase) {
        this.tryToGetOutOfJailOutputBoundary = tryToGetOutOfJailOutputBoundary;
        this.board = board;
        this.endTurnUseCase = endTurnUseCase;
        this.movePlayerUseCase = movePlayerUseCase;
    }

    @Override
    public void startAction(String playerOption, Player player) {
        if(playerOption.equals("Roll")) {
            // This is different from movePlayerUseCase as it doesn't take into account previous double rolls
            int[] playerRollAmount = {(int)(Math.random() * 6) + 1, (int)(Math.random() * 6) + 1};
            tryToGetOutOfJailOutputBoundary.showRoll(playerRollAmount);
            if(playerRollAmount[0] == playerRollAmount[1]) {
                // player rolled a double and are free
                player.resetTurnInJail();
                int rollSum = playerRollAmount[0] + playerRollAmount[1];
                movePlayerUseCase.movePlayer(player, rollSum, false);
            } else {
                // Player didn't roll double, force ending their turn
                player.addTurnInJail();
                endTurnUseCase.forceEndTurn(player);
            }
        } else if (playerOption.equals("Pay")) {
            player.subtractMoney(50);
            player.resetTurnInJail();
            // Normal roll dice move
            movePlayerUseCase.startAction(player, true);
        } else if (playerOption.equals("Card")) {
            player.removeGetOutOfJailCard();
            player.resetTurnInJail();
            // Normal roll dice move
            movePlayerUseCase.startAction(player, true);
        }
    }

    @Override
    public void getPlayerOptions(Player player) {
        ArrayList<String> playerOptions = new ArrayList<String>();
        playerOptions.add("Roll");
        if(player.getMoney() >= 50) {
            playerOptions.add("Pay");
        }
        if(player.hasGetOutofJailFreeCard()) {
            playerOptions.add("Card");
        }
        tryToGetOutOfJailOutputBoundary.showOptions(playerOptions);
    }
}
