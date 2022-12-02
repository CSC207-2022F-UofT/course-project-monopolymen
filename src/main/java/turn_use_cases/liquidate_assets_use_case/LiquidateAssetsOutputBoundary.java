package turn_use_cases.liquidate_assets_use_case;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

import java.util.ArrayList;
import java.util.List;

public interface LiquidateAssetsOutputBoundary {

    /**
     *
     * @param playerOptions The presenter will need the list of options the player has available
     * @param player The presenter will need the player that the use case applies to
     */
    public void showPlayerOptions(ArrayList<String> playerOptions, Player player);

    /**
     *
     * @param mortgageableProperties The presenter will need the list of properties that the player can
     *                               mortgage
     * @param player The presenter will need the player that the use case applies to
     */

    public void showMortgageableProperties(List<Property> mortgageableProperties, Player player);

    /**
     *
     * @param propertiesWithHouses The presenter will need the list of properties that have houses/hotels
     *                             that can be sold.
     * @param player The presenter will need the player that the use case applies to
     */

    public void showPropertiesWithHouses(List<ColorPropertyTile> propertiesWithHouses, Player player);

    /**
     *
     * @param bankruptedPlayer The presenter will need the player that the use case applied to
     * @param owedPlayer The presenter will need the player that the use case applied to
     *                   (This will likely just be a window or panel stating that the owedPlayer inherited
     *                   all of bankrupted player's assets
     */

    public void showTransferOfAssets(Player bankruptedPlayer, Player owedPlayer);
}
