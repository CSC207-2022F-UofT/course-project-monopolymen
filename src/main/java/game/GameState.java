package game;

import game.GameStateOutputBoundary.TurnActions;
import game_entities.Board;
import game_entities.Player;
import turn_interface_adapters.TurnController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A GameState object handles all information about the current Game's state (including who is playing, what turn it is).
 * GameState also handles logic for switching between turns.
 * <p>
 * Call the startGame method to initiate the game loop.
 */
public class GameState implements Serializable {
    private final List<Player> allPlayers;
    private final List<Player> activePlayers;
    private final String gameName;
    private final Board board;
    private int numPlayers;
    private transient SaveGameState saveGameState;
    private transient GameStateOutputBoundary presenter;
    private int currentPlayer;
    private int turnCounter;
    private boolean playerAllowedToEndTurn;

    /**
     * Construct a GameState object for the purposes of Turn Control and Game Save/Load.
     *
     * @param players       The list of Players in the game specifying the order that the players play in.
     * @param gameName      The name of this game. Also used for the save name.
     * @param board         The board associated with this gameState
     * @param saveGameState The SaveGameState object that handles saving the game.
     * @param presenter     The Presenter object for this game state.
     */
    public GameState(List<Player> players, String gameName, Board board, SaveGameState saveGameState, GameStateOutputBoundary presenter) {
        this.allPlayers = players;
        this.activePlayers = new ArrayList<>();
        this.currentPlayer = 0;
        this.numPlayers = allPlayers.size();
        this.saveGameState = saveGameState;
        this.turnCounter = 0;
        this.board = board;
        this.gameName = gameName;
        this.presenter = presenter;
        this.playerAllowedToEndTurn = false;
    }

    /**
     * Return the deserialized GameState object from the ObjectInputStream.
     * The SaveGameState data gateway and the GameStateOutputBoundary presenter are not serialized and so must be given
     * back to the GameState object in this method.
     *
     * @param objectIn      The ObjectInputStream representing the serialized object.
     * @param saveGameState The SaveGameState object responsible for saving GameState.
     * @param presenter     The Presenter for the GameState object.
     * @return The Deserialized GameState object.
     */
    public static GameState deserialize(ObjectInputStream objectIn, SaveGameState saveGameState,
                                        GameStateOutputBoundary presenter, TurnController turnController) throws ClassNotFoundException, IOException {
        GameState gameState = (GameState) objectIn.readObject();
        gameState.setSaveGameState(saveGameState);
        gameState.setPresenter(presenter);
        for (Player player : gameState.allPlayers) {
            player.setTurnController(turnController);
        }
        return gameState;
    }

    /**
     * Starts the game's first turn.
     * Turn Flow:
     * <ul>
     *     <li><i>startTurn</i> is called and determines the valid <i>TurnActions</i>> the player may choose from</li>
     *     <li><i>startTurn</i> calls the presenter to show the appropriate TurnActions as choices for the player
     *          to choose from. Player Choices link to <i>TurnController</i> methods</li>
     *     <li>The <i>TurnController</i> methods link to the appropriate Use Case (with player input if needed)</li>
     *     <li>The Player must take the ROLL_TO_MOVE action or the LEAVE_JAIL action before they are able to select
     *          the END_TURN action. When taking these options, GameState should be notified that player is allowed
     *          to end their turn via the <i>allowPlayerToEndTurn</i> method.</li>
     *     <li>The <i>EndTurn</i> Use Case links back to this <i>GameState</i>'s <i>endTurn</i> method</li>
     *     <li><i>endTurn</i> handles logic like autosave and calls <i>startTurn</i> for the next player</li>
     * </ul>
     */
    public void startGame() {
        activePlayers.addAll(allPlayers);
        numPlayers = activePlayers.size();
        // Shallow method to avoid possible confusion about needing to start each turn by method call every time.
        showTurnActions();
    }

    /**
     * Show the valid TurnActions that the player can take.
     * The following actions have validity conditions:
     * <ul>
     *     <li><b>ROLL_TO_MOVE</b> is only valid if the player isn't in jail and didn't roll already this turn.
     *          ROLL_TO_MOVE <i>is</i> valid if their previous roll this turn was doubles.</li>
     *     <li><b>LEAVE_JAIL</b> is only valid if the player is in jail and didn't already attempt to leave jail</li>
     *     <li><b>END_TURN</b> is only valid after a player has attempted to leave jail or rolled their dice</li>
     * </ul>
     */
    public void showTurnActions() {
        ArrayList<TurnActions> options = new ArrayList<>();

        // Check if the Player is allowed to end their turn.
        // The Player isn't allowed to end their turn until they roll to move or try to get out of jail.
        // The Player isn't allowed to move twice in a turn or attempt to leave jail twice in a turn.
        if (playerAllowedToEndTurn) {
            options.add(TurnActions.END_TURN);
        } else {
            if (currentPlayer().getTurnsInJail() == -1) {
                options.add(TurnActions.ROLL_TO_MOVE);
            } else {
                options.add(TurnActions.LEAVE_JAIL);
            }
        }

        // Potentially Logic to hide building buildings or mortgaging until conditions met.
        options.add(TurnActions.BUILD_BUILDING);
        options.add(TurnActions.SELL_BUILDING);
        options.add(TurnActions.MORTGAGE);
        options.add(TurnActions.UNMORTGAGE);
        options.add(TurnActions.TRADE);

        presenter.showTurnActions(currentPlayer(), options);
    }

    /**
     * Ends the current Player's turn and handles the logic of switching to the next player's turn.
     * To be called by the end_turn_use_case. Also shows the next player the turn actions they can take.
     */
    public void endTurn() {
        nextPlayer();
        boolean saved = saveGameState.save(this, "save_" + gameName);
        presenter.showAutosaveStatus(saved);
        // Start the next player's Turn.
        presenter.showNextTurn(currentPlayer());
    }

    /**
     * Returns whether this GameState is equal to the other object. Two GameState objects are considered equal if all
     * of their attributes are equal (excluding the SaveGameState and OutputBoundary objects).
     */
    @Override
    public boolean equals(Object o) {
        // Generated by Intellij
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return numPlayers == gameState.numPlayers && currentPlayer == gameState.currentPlayer && turnCounter == gameState.turnCounter && playerAllowedToEndTurn == gameState.playerAllowedToEndTurn && allPlayers.equals(gameState.allPlayers) && activePlayers.equals(gameState.activePlayers) && gameName.equals(gameState.gameName);
    }

    @Override
    public int hashCode() {
        // Generated by Intellij
        return Objects.hash(allPlayers, activePlayers, gameName, numPlayers, currentPlayer, turnCounter, playerAllowedToEndTurn);
    }

    /**
     * Decide if the player is allowed to keep rolling (primarily if they rolled doubles or not).
     * To be called after the Player moves. <p>
     * The Player isn't allowed to end their turn until they roll to move or try to get out of jail. <p>
     * The Player isn't allowed to move twice in a turn or attempt to leave jail twice in a turn.
     */
    public void playerRolledToMove() {
        // The player must keep rolling if they rolled doubles on their last roll.
        // Rolling 3 doubles lands them in jail, and they are no longer able to keep rolling
        // (and are not allowed to attempt to leave jail).
        playerAllowedToEndTurn = currentPlayer().getConsecutiveDoubles() <= 0
                || currentPlayer().getConsecutiveDoubles() >= 3;
    }

    /**
     * To be called after the player attempts to leave jail. Prevents the player from having LEAVE_JAIL as an option. <p>
     * The Player isn't allowed to end their turn until they roll to move or try to get out of jail. <p>
     * The Player isn't allowed to move twice in a turn or attempt to leave jail twice in a turn.
     */
    public void playerAttemptedLeaveJail() {
        playerAllowedToEndTurn = true;
    }

    /**
     * Return the current turn's Player.
     *
     * @return The Player whose turn it currently is.
     */
    public Player currentPlayer() {
        return activePlayers.get(currentPlayer);
    }

    /**
     * Gets a list of all the players in the game, whether they have lost or not.
     *
     * @return A list of all players in the game.
     */
    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    /**
     * Gets a list of all active players (currently in the game).
     *
     * @return A list of all players who are not out of the game.
     */
    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Returns whether the player given has gone bankrupt or not.
     *
     * @return True if the player has gone bankrupt, False otherwise.
     */
    public boolean hasLost(Player player) {
        return !activePlayers.contains(player);
    }

    /**
     * Removes the player from the ActivePlayers list, indicating that they have gone bankrupt.
     * Immediately starts the next player's turn.
     *
     * @param player The player who has gone bankrupt.
     */
    public void removePlayer(Player player) {
        activePlayers.remove(player);
        numPlayers = activePlayers.size();
        // Compensate for the player removed. Assumes nextPlayer is called right after to do (currentPlayer + 1) % numPlayers
        currentPlayer--;
        endTurn();
    }

    public void setSaveGameState(SaveGameState saveGameState) {
        this.saveGameState = saveGameState;
    }

    public void setPresenter(GameStateOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Increments private counters and resets turn-based logic.
     */
    private void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % numPlayers;
        turnCounter++;
        playerAllowedToEndTurn = false;
    }
}
