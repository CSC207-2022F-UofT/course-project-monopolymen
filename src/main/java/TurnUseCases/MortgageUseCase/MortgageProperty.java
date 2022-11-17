package TurnUseCases.MortgageUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

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
    public void mortgage(Player player, Property property){
        if (player.ownsProperty(property)) {
            player.addMoney(property.mortgage());
        }
    }


    /**
     * Overloading method mortgage. Player mortgages a color property.
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    public void mortgage(Player player, ColorPropertyTile property){
        if (player.ownsProperty(property) && property.getHouse() == 0 && property.getHotel() == 0) {
            player.addMoney(property.mortgage());
        }
    }

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     */
    public void unmortgage(Player player, Property property){
        if (player.ownsProperty(property)) {
            int value = 0;
            value = property.getMortgageValue();
            value = (int) (value * 1.1);
            player.subtractMoney(value);
            property.unmortgage();
        }
    }
}
