package TurnUseCases.TryToGetOutOfJailUseCase;
import GameEntities.Board;
import GameEntities.Player;
import GameEntities.Tiles.Property;
import GameEntities.Tiles.Tile;
import GameEntities.Tiles.TileActionResultModel;
import GameEntities.Tiles.TilePassResultModel;
import TurnUseCases.MovePlayerUseCase.MovePlayerUseCase;
import TurnUseCases.EndTurnUseCase.EndTurnUseCase;
import java.util.ArrayList;
import GameEntities.Cards.CardActionResultModel;

/**
 * TryToGetOutOfJailUseCase (Class to handle the player's actions when they are in jail)
 * MovePlayerUseCase will handle the 3 cases: pay 50$, use "get out of jail" card, and roll for double
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
                for(int i = 0; i < rollSum; i++) {
                    player.updatePosition(1);
                    TilePassResultModel passResult = board.getTile(player.getPosition()).passing(player);
                    tryToGetOutOfJailOutputBoundary.showResultOfPass(player, player.getPosition(), passResult);
                }
                int playerBeforePosition = player.getPosition();
                TileActionResultModel result = board.getTile(player.getPosition()).action(player);
                if(result instanceof CardActionResultModel) {
                    // Player landed on draw card tile
                    if (playerBeforePosition != result.getPlayerPosition()) {
                        // Card moved player
                        if (result.getPlayerPosition() == -1) {
                            // Player is moving to jail, does not collect "GO" tile money
                            // player.enterJail() is handled in the card's action
                            tryToGetOutOfJailOutputBoundary.showCardDraw(player, result.getCardName(),
                                    result.getFlavorText(), result.isChance());
                            sendToJail(player);
                        } else {
                            // Normal move player card
                            tryToGetOutOfJailOutputBoundary.showCardDraw(player, result.getCardName(),
                                    result.getFlavorText(), result.isChance());
                        }
                    } else {
                        // Card didn't move player
                        tryToGetOutOfJailOutputBoundary.showCardDraw(player, result.getCardName(),
                                result.getFlavorText(), result.isChance());
                    }
                } else {
                    // Player didn't land on a draw card tile
                    Tile tile = board.getTile(player.getPosition());
                    if (result.getPlayerPosition() == -1) {
                        // Player landed on "go to jail" and their position should now be in jail
                        // player.enterJail() is handeled in the tile's action method
                        sendToJail(player);
                    } else {
                        // Normal move
                        tryToGetOutOfJailOutputBoundary.showResultOfAction(player, player.getPosition(),
                                result.getFlavorText());
                        if (tile instanceof Property) {
                            if (((Property) tile).getOwner() == null) {
                                tryToGetOutOfJailOutputBoundary.showBuyableProperty(player, tile);
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
        if(player.getGetOutOfJailCard()) {
            playerOptions.add("Card");
        }
        return playerOptions;
    }

    @Override
    public void sendToJail(Player player) {
        tryToGetOutOfJailOutputBoundary.showResultOfAction(player, player.getPosition(),
                "You are being sent to jail.");
        endTurnUseCase.forceEndTurn(player);
    }
}
