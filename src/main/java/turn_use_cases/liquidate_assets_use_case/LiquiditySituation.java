package turn_use_cases.liquidate_assets_use_case;

import game.GameState;
import game_entities.Board;
import game_entities.Player;

public class LiquiditySituation {
    private final Player affectedPlayer;
    private final Player owedPlayer;

    public Player getAffectedPlayer() {
        return affectedPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getOwedPlayer() {
        return owedPlayer;
    }

    public int getOwedMoney() {
        return owedMoney;
    }

    private final int owedMoney;
    private final GameState gameState;

    private final Board board;

    public Board getBoard() {
        return board;
    }

    public LiquiditySituation(Player affectedPlayer, Player owedPlayer, int owedMoney, GameState gameState, Board board) {
        this.affectedPlayer = affectedPlayer;
        this.owedPlayer = owedPlayer;
        this.owedMoney = owedMoney;
        this.gameState = gameState;
        this.board = board;
    }
}
