package turn_use_cases.try_to_get_out_of_jail_use_case;

import game_entities.Board;
import game_entities.Player;
import turn_use_cases.end_turn_use_case.EndTurnInputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerInputBoundary;
import turn_use_cases.move_player_use_case.MovePlayerOutputBoundary;

import java.util.ArrayList;

/**
 * try_to_get_out_of_jail_use_case (Class to handle the player's actions when they are in jail)
 * move_player_use_case will handle the 3 cases: pay 50$, use "get out of jail" card, and roll for double
 * If the player rolls a double in jail, they will move the distance they rolled, but they cannot roll again
 */
public class TryToGetOutOfJailUseCase implements TryToGetOutOfJailInputBoundary {
    private final TryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary;
    private Board board;
    private EndTurnInputBoundary endTurnUseCase;
    private final MovePlayerInputBoundary movePlayerUseCase;
    private final MovePlayerOutputBoundary movePlayerOutputBoundary;
    /**
     * @param tryToGetOutOfJailOutputBoundary TryToGetOutOfJailOutputBoundary to handle the display
     * @param movePlayerUseCase Use Case to move the player if they get out of jail
     * @param movePlayerOutputBoundary Output boundary to display visuals
     */
    public TryToGetOutOfJailUseCase(TryToGetOutOfJailOutputBoundary tryToGetOutOfJailOutputBoundary,
                                    MovePlayerInputBoundary movePlayerUseCase,
                                    MovePlayerOutputBoundary movePlayerOutputBoundary) {
        this.tryToGetOutOfJailOutputBoundary = tryToGetOutOfJailOutputBoundary;
        this.movePlayerUseCase = movePlayerUseCase;
        this.movePlayerOutputBoundary = movePlayerOutputBoundary;
    }

    @Override
    public void startAction(String playerOption, Player player) {
        System.out.println(playerOption);
        switch (playerOption) {
            case "Roll":
                // This is different from movePlayerUseCase as it doesn't take into account previous double rolls
                int[] playerRollAmount = {(int) (Math.random() * 6) + 1, (int) (Math.random() * 6) + 1};
                movePlayerOutputBoundary.showRoll(playerRollAmount);
                if (playerRollAmount[0] == playerRollAmount[1]) {
                    // player rolled a double and are free
                    player.resetTurnInJail();
                    int rollSum = playerRollAmount[0] + playerRollAmount[1];
                    movePlayerUseCase.movePlayer(player, rollSum, false);
                } else {
                    // Player didn't roll double, force ending their turn
                    player.addTurnInJail();
                    tryToGetOutOfJailOutputBoundary.showRoll(playerRollAmount);
                }
                break;
            case "Pay":
                player.subtractMoney(50);
                player.resetTurnInJail();
                // Normal roll dice move
                movePlayerUseCase.startAction(player, true);
                break;
            case "Card":
                player.removeGetOutOfJailCard();
                player.resetTurnInJail();
                // Normal roll dice move
                movePlayerUseCase.startAction(player, true);
                break;
        }
    }

    @Override
    public void getPlayerOptions(Player player) {
        System.out.println(player.numGetOutofJailFreeCards());
        ArrayList<String> playerOptions = new ArrayList<>();
        playerOptions.add("Roll");
        if(player.getMoney() >= 50) {
            playerOptions.add("Pay");
        }
        if(player.hasGetOutofJailFreeCard()) {
            playerOptions.add("Card");
        }
        tryToGetOutOfJailOutputBoundary.showOptions(player, playerOptions);
    }
}
