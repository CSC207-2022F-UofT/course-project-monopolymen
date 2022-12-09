package turn_use_cases.move_player_use_case;

import game_entities.Board;
import game_entities.Player;
import game_entities.cards.CardActionResultModel;
import game_entities.tiles.*;
import turn_use_cases.end_turn_use_case.EndTurnInputBoundary;

/**
 * move_player_use_case (Class to handle moving the player and all its relevant logic such as passing a tile and landing
 * actions)
 * move_player_use_case will handle the cases when the player rolls 3 consecutive doubles or land on "go to jail" and send
 * them to jail, otherwise, it will be a standard move with it moving the player to their final locations and calling
 * the tile's .action method
 */
public class MovePlayerUseCase implements MovePlayerInputBoundary {

    private final MovePlayerOutputBoundary movePlayerOutputBoundary;
    private final Board board;
    private final EndTurnInputBoundary endTurnUseCase;
    /**
     * @param movePlayerOutputBoundary MovePlayerOutputBoundary to handle display
     * @param board The board the game is operating on
     * @param endTurnUseCase class to force end a turn if the player is sent to jail
     */
    public MovePlayerUseCase(MovePlayerOutputBoundary movePlayerOutputBoundary, Board board,
                             EndTurnInputBoundary endTurnUseCase) {
        this.movePlayerOutputBoundary = movePlayerOutputBoundary;
        this.board = board;
        this.endTurnUseCase = endTurnUseCase;
    }

    /**
     * Starts the logic of moving the player to the position
     * This is only called by startAction when it lands on a draw card tile that will move the player
     * @param player The player object that the action is being performed on
     * @param absolutePosition the position the player will move to
     * @param doubleRoll If the player rolled a double
     * @param flavorText FlavorText
     */

    private void moveToPosition(Player player, int absolutePosition, boolean doubleRoll, String flavorText) {
        // There is only 1 case when the player moves back by the card and its 3 spaces
        int steps = absolutePosition - player.getPosition();
        if(steps == -3) {
            // Only move backwards case
            showAction(player, doubleRoll, flavorText);
        } else {
            int positiveSteps = (steps + board.getTilesList().size()) % board.getTilesList().size();
            for (int i = 0; i < positiveSteps; i++) {
                player.updatePosition(1);
                TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
            }
            showAction(player, doubleRoll, flavorText);
        }
    }

    /**
     * Shows the action in the presenter
     * @param player The player object that the action is being performed on
     * @param doubleRoll If the player rolled a double
     */
    private void showAction(Player player, boolean doubleRoll, String flavorText) {
        Tile tile = board.getTile(player.getPosition());
        if(tile.isOwnable()) {
            if(((Property)tile).getOwner() == null) {
                movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                        flavorText, "Don't Buy");
            } else {
                movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                        flavorText, "Other Options");
            }
        } else {
            movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                    flavorText, "Other Options");
        }
        if (tile instanceof Property) {
            movePlayerOutputBoundary.showBuyableProperty(player, tile, !((Property) tile).isOwned(), doubleRoll);
        }
    }

    @Override
    public void startAction(Player player, boolean canRollAgain) {
        int[] playerRollAmount = {(int)(Math.random() * 6) + 1, (int)(Math.random() * 6) + 1};
        movePlayerOutputBoundary.showRoll(playerRollAmount);
        int rollSum = playerRollAmount[0] + playerRollAmount[1];
        boolean doubleRoll = playerRollAmount[0] == playerRollAmount[1];
        if (!canRollAgain) {
            // player is not allowed to roll again even if they roll a double
            doubleRoll = false;
        }
        // Check if the player is in jail will be handled separately
        player.updateConsecutiveDoubles(playerRollAmount[0], playerRollAmount[1]);
        player.setLastRoll(playerRollAmount[0], playerRollAmount[1]);
        if (player.getConsecutiveDoubles() == 3) {
            player.enterJail();
            movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                    "You are going to jail for rolling three consecutive doubles.", "Other Options");
            endTurnUseCase.forceEndTurn(player);
        } else {
            movePlayer(player, rollSum, doubleRoll);
        }
    }

    @Override
    public void movePlayer(Player player, int rollSum, boolean doubleRoll) {
        int playerBeforePosition = player.getPosition();
        int playerAfterPosition = (player.getPosition() + rollSum) % board.getTilesList().size();
        TileActionResultModel result = board.getTile(playerAfterPosition).action(player, board);
        if(result instanceof CardActionResultModel) {
            // Player landed on draw card tile
            CardActionResultModel cardResult = (CardActionResultModel) result;
            if(playerBeforePosition != result.getPlayerPosition()){
                // Card moved player
                if(result.getPlayerPosition() == board.getJailTilePosition()) {
                    // Player is moving to jail, does not collect "GO" tile money
                    // player.enterJail() is handled in the card's action
                    movePlayerOutputBoundary.showCardDraw(player, cardResult.getCardName(), false, cardResult.isChance());
                    sendToJail(player);
                } else {
                    // Normal move player card
                    movePlayerOutputBoundary.showCardDraw(player, cardResult.getCardName(), doubleRoll, cardResult.isChance());
                    moveToPosition(player, cardResult.getPlayerPosition(), doubleRoll, cardResult.getFlavorText());
                }
            } else {
                // Card didn't move player
                moveToPosition(player, playerAfterPosition, doubleRoll, cardResult.getFlavorText());
                movePlayerOutputBoundary.showCardDraw(player, cardResult.getCardName(),
                        doubleRoll, cardResult.isChance());
            }
        } else {
            // Player didn't land on a draw card tile
            if (result.getPlayerPosition() == board.getJailTilePosition()) {
                // Player landed on "go to jail" and their position should now be in jail
                // player.enterJail() is handeled in the tile's action method
                sendToJail(player);
            } else {
                // Normal move
                moveToPosition(player, playerAfterPosition, doubleRoll, result.getFlavorText());
                }
            }
    }

    /**
     * Sends the player to jail
     * Only called by methods in move_player_use_case
     * @param player The player object that the action is being performed on
     */
    private void sendToJail(Player player) {
        movePlayerOutputBoundary.showResultOfAction(player, board.getJailTilePosition(), false,
                "You are being sent to jail.", "Other Options");
        endTurnUseCase.forceEndTurn(player);
    }
}
