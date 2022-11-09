package TurnUseCases.MortageUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;

/**
 * All methods related to mortgage.
 */
public class MortgageProperty {

    /**
     * Player mortgages property
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    public boolean mortgage(Player player, ColorPropertyTile property){
        if (player.ownsProperty(property)) {
            player.addMoney(property.mortgage());
            return true;
        }
        return false;
    }

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     */
    public boolean unmortgage(Player player, ColorPropertyTile property){
        if (player.ownsProperty(property)) {
            int value = 0;
            value = property.getMortgageValue();
            value = (int) (value * 1.1);
            player.subtractMoney(value);
            property.unmortgage();
            return true;
        }
        return false;
    }
}
