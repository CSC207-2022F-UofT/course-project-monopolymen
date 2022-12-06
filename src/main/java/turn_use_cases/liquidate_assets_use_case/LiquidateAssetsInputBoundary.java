package turn_use_cases.liquidate_assets_use_case;

import game.GameState;
import game_entities.Player;


public interface LiquidateAssetsInputBoundary {
    /**
     * This gets the possible options of the affectedPlayer in situation
     */
    public void getPlayerOptions(LiquiditySituation situation);

    /**
     *This gets the list of mortgageable properties from affectedPlayer
     */

    public void getMortgageableProperties(LiquiditySituation situation);

    /**
     *This gets the list of properties with houses that are sellable
     */

    public void getPropertiesWithHouses(LiquiditySituation situation);

    /**
     *This implements the affectedPlayer getting bankrupted by the owedPlayer
     */

    public void bankruptcy(LiquiditySituation situation);
}
