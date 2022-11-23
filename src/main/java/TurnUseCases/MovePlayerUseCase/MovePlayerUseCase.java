package TurnUseCases.MovePlayerUseCase;
import GameEntities.Player;
import GameEntities.Tiles.Property;
import GameEntities.Tiles.Tile;
import GameEntities.Tiles.TileActionResultModel;
import GameEntities.Board;
import GameEntities.Tiles.TilePassResultModel;
import TurnUseCases.EndTurnUseCase.EndTurnUseCase;

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

    @Override
    public void moveToPosition(Player player, int absolutePosition) {
        int steps = absolutePosition - player.getPosition();
        for(int i = 0; i < steps; i++) {
            player.updatePosition(1);
            TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
            movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult,
                    passResult.getFlavorText());
        }
        TileActionResultModel result = board.getTile(player.getPosition()).action(player);
        Tile tile = board.getTile(player.getPosition());
        movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                result.getFlavorText());
        if(tile instanceof Property) {
            if(((Property) tile).getOwner() == null) {
                movePlayerOutputBoundary.showBuyableProperty(player, tile);
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
                movePlayerOutputBoundary.showResultOfPass(player, player.getPosition(), passResult,
                        passResult.getFlavorText());
            }
            // Player passed through all the tiles and is now on their original position + rollSum
            TileActionResultModel result = board.getTile(player.getPosition()).action(player);
            if(result instanceof DrawCardTileResultModel) {
                // Player landed on draw card tile
                int position = (DrawCardTileResultModel)result.getPlayerPosition();
                if(position != player.getPosition()){
                    // Card moved player
                    if(position == -1) {
                        // Player is moving to jail, does not collect "GO" tile money
                        player.enterJail();
                        movePlayerOutputBoundary.showCardDraw(player, result.getTileName(), result.getTileDescription(),
                                result.getIsChance());
                        movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                                "You are being sent to jail.");
                        endTurnUseCase.forceEndTurn(player);
                    } else {
                        // Normal move player card
                        movePlayerOutputBoundary.showCardDraw(player, result.getTileName(), result.getTileDescription(),
                                result.getIsChance());
                        moveToPosition(player, result.getPlayerPosition());
                    }
                } else {
                    // Card didn't move player
                    movePlayerOutputBoundary.showCardDraw(player, result.getTileName(), result.getTileDescription(),
                            result.getIsChance());
                }
            } else {
                // Player didn't land on a draw card tile
                Tile tile = board.getTile(player.getPosition());
                if (result.getPlayerPosition() == -1) {
                    // Player landed on "go to jail" and their position should now be in jail
                    player.enterJail();
                    movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), false,
                            result.getFlavorText());
                    endTurnUseCase.forceEndTurn(player);
                } else {
                    // Normal move
                    movePlayerOutputBoundary.showResultOfAction(player, player.getPosition(), doubleRoll,
                            result.getFlavorText());
                    if (tile instanceof Property) {
                        if (((Property) tile).getOwner() == null) {
                            movePlayerOutputBoundary.showBuyableProperty(player, tile);
                        }
                    }
                }
            }
        }
    }
}
