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

    /**
     * Starts the logic of moving the player to the position
     * This is only called by startAction when it lands on a draw card tile that will move the player
     * @param player The player object that the action is being performed on
     * @param absolutePosition the position the player will move to
     */

    private void moveToPosition(Player player, int absolutePosition) {
        // There is only 1 case when the player moves back by the card and its 3 spaces
        int steps = absolutePosition - player.getPosition();
        if(steps == -3) {
            // Only move backwards case
            for (int i = 0; i < steps; i++) {
                player.updatePosition(-1);
                if(board.getTile(player.getPosition()) instanceof GoTile) {
                    // Player will pass but not collect money from "GO"
                    TilePassResultModel nullPass = new TilePassResultModel(false, "");
                    tryToGetOutOfJailOutputBoundary.showResultOfPass(player, player.getPosition(), nullPass);
                } else {
                    // Normal backwards pass
                    TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                    tryToGetOutOfJailOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
                }
            }
            showAction(player);
        } else {
            int positiveSteps = (steps + board.getTilesList().size()) % board.getTilesList().size();
            for (int i = 0; i < positiveSteps; i++) {
                player.updatePosition(1);
                TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                tryToGetOutOfJailOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
            }
            showAction(player);
        }
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
                for(int i = 0; i < rollSum; i++) {
                    player.updatePosition(1);
                    TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                    tryToGetOutOfJailOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
                }
                int playerBeforePosition = player.getPosition();
                TileActionResultModel result = board.getTile(player.getPosition()).action(player, board);
                if(result instanceof CardActionResultModel) {
                    // Player landed on draw card tile
                    CardActionResultModel cardResult = (CardActionResultModel) result;
                    if (playerBeforePosition != result.getPlayerPosition()) {
                        // Card moved player
                        if (result.getPlayerPosition() == board.getJailTilePosition()) {
                            // Player is moving to jail, does not collect "GO" tile money
                            // player.enterJail() is handled in the card's action
                            tryToGetOutOfJailOutputBoundary.showCardDraw(player, cardResult.getCardName(),
                                    cardResult.getFlavorText(), cardResult.isChance());
                            sendToJail(player);
                        } else {
                            // Normal move player card
                            tryToGetOutOfJailOutputBoundary.showCardDraw(player, cardResult.getCardName(),
                                    cardResult.getFlavorText(), cardResult.isChance());
                            moveToPosition(player, cardResult.getPlayerPosition());
                        }
                    } else {
                        // Card didn't move player
                        tryToGetOutOfJailOutputBoundary.showCardDraw(player, cardResult.getCardName(),
                                cardResult.getFlavorText(), cardResult.isChance());
                    }
                } else {
                    // Player didn't land on a draw card tile
                    Tile tile = board.getTile(player.getPosition());
                    if (result.getPlayerPosition() == board.getJailTilePosition()) {
                        // Player landed on "go to jail" and their position should now be in jail
                        // player.enterJail() is handeled in the tile's action method
                        sendToJail(player);
                    } else {
                        // Normal move
                        tryToGetOutOfJailOutputBoundary.showResultOfAction(player, player.getPosition(),
                                result.getFlavorText());
                        if (tile instanceof Property) {
                            if (((Property) tile).getOwner() == null) {
                                tryToGetOutOfJailOutputBoundary.showBuyableProperty(player, tile, true);
                            } else {
                                tryToGetOutOfJailOutputBoundary.showBuyableProperty(player, tile, false);;
                            }
                        }
                    }
                }
            } else {
                // Player didn't roll double, force ending their turn
                player.addTurnInJail();
                endTurnUseCase.forceEndTurn(player);
            }
        } else if (playerOption.equals("Pay")) {
            player.subtractMoney(50);
            player.resetTurnInJail();
            // Normal roll dice move
            movePlayerUseCase.startAction(player);
        } else if (playerOption.equals("Card")) {
            player.removeGetOutOfJailCard();
            player.resetTurnInJail();
            // Normal roll dice move
            movePlayerUseCase.startAction(player);
        }
    }

    @Override
    public ArrayList<String> getPlayerOptions(Player player) {
        ArrayList<String> playerOptions = new ArrayList<String>();
        playerOptions.add("Roll");
        if(player.getMoney() >= 50) {
            playerOptions.add("Pay");
        }
        if(player.hasGetOutofJailFreeCard()) {
            playerOptions.add("Card");
        }
        return playerOptions;
    }

    /**
     * Sends the player to jail
     * Only called by methods in try_to_get_out_of_jail_use_case
     * @param player The player object that the action is being performed on
     */
    private void sendToJail(Player player) {
        tryToGetOutOfJailOutputBoundary.showResultOfAction(player, player.getPosition(),
                "You are being sent to jail.");
        endTurnUseCase.forceEndTurn(player);
    }

    /**
     * Shows the action in the presenter
     * @param player The player object that the action is being performed on
     */
    private void showAction(Player player) {
        TileActionResultModel result = board.getTile(player.getPosition()).action(player, board);
        Tile tile = board.getTile(player.getPosition());
        tryToGetOutOfJailOutputBoundary.showResultOfAction(player, player.getPosition(),
                result.getFlavorText());
        if (tile instanceof Property) {
            if (((Property) tile).getOwner() == null) {
                tryToGetOutOfJailOutputBoundary.showBuyableProperty(player, tile, true);
            } else {
                tryToGetOutOfJailOutputBoundary.showBuyableProperty(player, tile, false);
            }
        }
    }
}
