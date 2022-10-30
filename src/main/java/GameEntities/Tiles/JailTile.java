package GameEntities.Tiles;

import java.util.Scanner;

public class JailTile extends Tile{
    /**
     * A player landing on the space cannot move for 3 turns unless they pay or have "get out of jail" card.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    protected JailTile(String tileName, String tileDisplayName, boolean ownable) {
        super("JailTile", "jail Tile", false);
    }

    /**
     * When lands on this tile, Player <i>player</i> cannot move for the next 3 turns unless they pay $50 to the bank
     * or owns and uses a "get out of jail" card
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        Scanner options = new Scanner(System.in);
        if (player.getGetOutOfJailCard()) {
            //basic idea, there will be no print statments in final inplementation.
            System.out.println("You have the option to use your get out of jail card:");
            int option = options.nextInt();
            if (option == 1) {
                player.RemoveGetOutOfJailCard();
                player.resetTurnInJail();
                // need something to tell the gamestate that it is still the players turn and they can still roll.
                return new TileActionResultModel("You used your get out of jail card!", -1);
            } else {
                player.addTurnInJail();
                // player turn ends here.
                return new TileActionResultModel("You didn't use your get out of jail card", -1)
            }
        } else {
            if (player.getMoney() >= 50) {
                System.out.println("Do you wish to spend $50 to get out of jail?");
                int option = options.nextInt();
                if (option == 1) {
                    player.subtractMoney(50);
                    player.resetTurnInJail();
                    //player turn doesn't end here.
                    return new TileActionResultModel("You paid $50 to get out of jail!", -1);
                } else {
                    player.addTurnInJail();
                    //player turn ends here.
                    return new TileActionResultModel("You didn't pay $50 to get out of jail", -1);
                }
            } else {
                System.out.println("You cannot leave jail");
                player.addTurnInJail();
                return new TileActionResultModel("You cannot leave jail", -1);
            }
        }
    }
}
