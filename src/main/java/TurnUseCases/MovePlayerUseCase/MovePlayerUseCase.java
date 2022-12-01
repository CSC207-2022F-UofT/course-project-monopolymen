package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;
import GameEntities.Tiles.*;
import GameEntities.Board;
import TurnUseCases.EndTurnUseCase.EndTurnUseCase;
import GameEntities.Cards.CardActionResultModel;

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
    private EndTurnUseCase endTurnUseCase;
    /**
     * @param movePlayerOutputBoundary MovePlayerOutputBoundary to handle display
     * @param board The board the game is operating on
     * @param endTurnUseCase class to force end a turn if the player is sent to jail
     */
    public MovePlayerUseCase(MovePlayerOutputBoundary movePlayerOutputBoundary, Board board,
                             EndTurnUseCase endTurnUseCase) {
        this.movePlayerOutputBoundary = movePlayerOutputBoundary;
        this.board = board;
        this.endTurnUseCase = endTurnUseCase;
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
                    movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), nullPass);
                } else {
                    // Normal backwards pass
                    TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                    movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
                }
            }
            showAction(player);
        } else {
            int positiveSteps = (steps + board.getTilesList().size()) % board.getTilesList().size();
            for (int i = 0; i < positiveSteps; i++) {
                player.updatePosition(1);
                TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
            }
            showAction(player);
        }
    }

    /**
     * Shows the action in the presenter
     * @param player The player object that the action is being performed on
     */
    private void showAction(Player player) {
        TileActionResultModel result = board.getTile(player.getPosition()).action(player, board);
        Tile tile = board.getTile(player.getPosition());
        movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                result.getFlavorText());
        if (tile instanceof Property) {
            if (((Property) tile).getOwner() == null) {
                movePlayerOutputBoundary.showBuyableProperty(player, tile, true);
            } else {
                movePlayerOutputBoundary.showBuyableProperty(player, tile, false);
            }
        }
    }

    @Override
    public void startAction(Player player) {
        int[] playerRollAmount = {(int)(Math.random() * 6) + 1, (int)(Math.random() * 6) + 1};
        movePlayerOutputBoundary.showRoll(playerRollAmount);
        int rollSum = playerRollAmount[0] + playerRollAmount[1];
        boolean doubleRoll = playerRollAmount[0] == playerRollAmount[1];
        // Check if the player is in jail will be handled separately
        player.updateConsecutiveDoubles(playerRollAmount[0], playerRollAmount[1]);
        player.setLastRoll(playerRollAmount[0], playerRollAmount[1]);
        if (player.getConsecutiveDoubles() == 3) {
            player.enterJail();
            movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                    "You are going to jail for rolling three consecutive doubles.");
            endTurnUseCase.forceEndTurn(player);
        } else {
            // Presenter calls to show player passing through the tiles
            for(int i = 0; i < rollSum; i++) {
                player.updatePosition(1);
                TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
            }
            // Player passed through all the tiles and is now on their original position + rollSum
            int playerBeforePosition = player.getPosition();
            TileActionResultModel result = board.getTile(player.getPosition()).action(player, board);
            if(result instanceof CardActionResultModel) {
                // Player landed on draw card tile
                CardActionResultModel cardResult = (CardActionResultModel) result;
                if(playerBeforePosition != result.getPlayerPosition()){
                    // Card moved player
                    if(result.getPlayerPosition() == board.getJailTilePosition()) {
                        // Player is moving to jail, does not collect "GO" tile money
                        // player.enterJail() is handled in the card's action
                        movePlayerOutputBoundary.showCardDraw(player, cardResult.getCardName(),
                                cardResult.getFlavorText(), cardResult.isChance());
                        sendToJail(player);
                    } else {
                        // Normal move player card
                        movePlayerOutputBoundary.showCardDraw(player, cardResult.getCardName(),
                                cardResult.getFlavorText(), cardResult.isChance());
                        moveToPosition(player, result.getPlayerPosition());
                    }
                } else {
                    // Card didn't move player
                    movePlayerOutputBoundary.showCardDraw(player, cardResult.getCardName(), cardResult.getFlavorText(),
                            cardResult.isChance());
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
                    movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), doubleRoll,
                            result.getFlavorText());
                    if (tile instanceof Property) {
                        if (((Property) tile).getOwner() == null) {
                            movePlayerOutputBoundary.showBuyableProperty(player, tile, true);
                        } else {
                            movePlayerOutputBoundary.showBuyableProperty(player, tile, false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Sends the player to jail
     * Only called by methods in MovePlayerUseCase
     * @param player The player object that the action is being performed on
     */
    private void sendToJail(Player player) {
        movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                "You are being sent to jail.");
        endTurnUseCase.forceEndTurn(player);
    }


}
