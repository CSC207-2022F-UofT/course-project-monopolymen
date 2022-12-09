package turn_use_cases.liquidate_assets_use_case;

import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

import java.util.ArrayList;
import java.util.List;

public interface LiquidateAssetsOutputBoundary {

    /**
     *
     * @param playerOptions The presenter will need the list of options the player has available
     */
    void showPlayerOptions(ArrayList<String> playerOptions, LiquiditySituation situation);

    /**
     *
     * @param mortgageableProperties The presenter will need the list of properties that the player can
     *                               mortgage
     */

    void showMortgageableProperties(List<Property> mortgageableProperties, LiquiditySituation situation);

    /**
     *
     * @param propertiesWithHouses The presenter will need the list of properties that have houses/hotels
     *                             that can be sold.
     */

    void showPropertiesWithHouses(List<ColorPropertyTile> propertiesWithHouses, LiquiditySituation situation);

    /**
     *
     * @param situation holds all the information as to who will be bankrupted and by who
     */

    void showTransferOfAssets(LiquiditySituation situation);
}
