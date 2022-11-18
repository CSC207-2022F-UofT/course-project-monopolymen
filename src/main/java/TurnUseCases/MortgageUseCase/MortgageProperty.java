package TurnUseCases.MortgageUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

/**
 * All methods related to mortgage.
 */
public class MortgageProperty implements MortgagePropertyInputBoundary{

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
        int mortgageValue = property.getMortgageValue();
        String text = player.getName() + "mortgaged " + property.getTileName() + "and get $" + mortgageValue;
        TurnActionPresenter.showMortgageProperty(player, property, text);
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
        int mortgageValue = property.getMortgageValue();
        String text = player.getName() + "mortgaged " + property.getTileName() + "and get $" + mortgageValue;
        TurnActionPresenter.showMortgageProperty(player, property, text);
    }

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     */
    public void unmortgage(Player player, Property property){
        int mortgageValue = 0;
        mortgageValue = property.getMortgageValue();
        mortgageValue = (int) (mortgageValue * 1.1);
        if (player.ownsProperty(property)) {
            player.subtractMoney(mortgageValue);
            property.unmortgage();
        }
        String text = player.getName() + "unmortgaged " + property.getTileName() + "and subtract $" + Integer.toString(mortgageValue);
        TurnActionPresenter.showUnmortgageProperty(player, property, text);
    }
}
