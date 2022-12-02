package turn_use_cases.liquidate_assets_use_case;

import game.GameState;
import game_entities.Player;


public interface LiquidateAssetsInputBoundary {
    /**
     *
     * @param player This is the player that owes money to another player or the bank. This parameter is
     *               used to get a list of the players properties to check whether the player has
     *               properties that have houses that can be sold or properties that can be mortgaged.
     * @param moneyOwed This is the amount of money owed. getPlayerOptions should be called whenever the
     *                  player mortgages a property, sells a house or trades with a player. If
     *                  player.getMoney() is greater than or equal to moneyOwed, than the Pay Owed Money
     *                  option will become available (the other options will cease to be available) and
     *                  the Liquidate Assets Use Case finishes.
     * @return These are the options that the player has to avoid bankruptcy
     */
    public void getPlayerOptions(Player player,int moneyOwed);

    /**
     *
     * @param player This is the player that owes money. The player variable is used to get the list of
     *               properties the player owns so that the method can check which properties the player
     *               can mortgage.
     * @return This is a dictionary of properties that the player can mortgage as well as it's mortgage
     *  value
     */

    public void getMortgageableProperties(Player player);

    /**
     *
     * @param player This is the player that owes money. The player variable is used to get the list of
     *               properties the player owns so that the method can check which properties the player
     *               has that have houses/hotels.
     * @return This is a dictionary of properties that the player owns which have houses/hotels on them
     * as well as the sell value of the house/hotel.
     */

    public void getPropertiesWithHouses(Player player);

    /**
     *
     * @param bankruptPlayer This is the player that is going bankrupt.
     * @param owedPlayer This is the player (or the bank if it's null) that has bankrupted the bankrupt
     *                   player. All the player's money and properties (as well as the houses that
     *                   are currently on the properties) will be transferred to the owedPlayer (or will
     *                   be set back to no one if they were bankrupted by the bank).
     */

    public void bankruptcy(Player bankruptPlayer, Player owedPlayer, GameState gameState);
}
