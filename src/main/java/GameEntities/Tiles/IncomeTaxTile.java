package GameEntities.Tiles;

import java.util.Scanner;

public class IncomeTaxTile extends Tile{
    /**
     * A player landing on the space must pay a income tax to the Bank.
     *
     * @param tileName        The internal string name representing this tile
     *                        (intended to not contain spaces or other special characters).
     * @param tileDisplayName The string name displayed to the user. This may have special characters.
     * @param ownable         Whether the tile is able to be owned by a player. This is protected with the intention that
     *                        only subclasses of Tile can be ownable.
     * @see GameEntities.Tiles.Property
     */
    protected IncomeTaxTile(String tileName, String tileDisplayName, boolean ownable) {
        super("IncomeTaxTile", "Income Tax Tile", false);
    }

    /**
     * When lands on this tile, Player <i>player</i> pays $200 or 10% of your total worth in cash as income tax to bank.
     *
     * @param player The Player that the action is being performed on (landed on the tile)
     * @return A TilePassResultModel object describing the action that was performed
     */
    @Override
    public TileActionResultModel action(Player player) {
        System.out.println("You have to pay tax:");
        System.out.println("Option 1: pay $200");
        System.out.println("Option 1: 10% of your total worth in cash");
        Scanner options = new Scanner(System.in);
        System.out.println("Enter 1 or 2:");
        int option = options.nextInt();
        if (option == 1){
            Player.money -= 200;
            return new TileActionResultModel("You paid $200 income tax!", 0);
        } else {
            int tax = Player.money * 0.1;
            Player.money -= tax;
            return new TileActionResultModel("You paid " + tax + "income tax!", 0);
        }
    }
}
