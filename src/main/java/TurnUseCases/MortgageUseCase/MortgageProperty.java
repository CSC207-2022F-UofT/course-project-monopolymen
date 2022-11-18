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
     * @param presenter presenter which provides info for players.
     */
    @Override
    public void mortgage(Player player, Property property, MortgagePropertyOutputBoundary presenter){
        if (player.ownsProperty(property)) {
            player.addMoney(property.mortgage());
            int mortgageValue = property.getMortgageValue();
            String text = player.getName() + "mortgaged " + property.getTileName() + "and get $" + mortgageValue;
            presenter.showMortgageProperty(player, property, text);
        } else {
            String text = player.getName() + "cannot mortgage " + property.getTileName();
            presenter.showMortgageProperty(player, property, text);
        }
    }


    /**
     * Overloading method mortgage. Player mortgages a color property.
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     * @param presenter presenter which provides info for players.
     */
    @Override
    public void mortgage(Player player, ColorPropertyTile property, MortgagePropertyOutputBoundary presenter){
        if (player.ownsProperty(property) && property.getHouse() == 0 && property.getHotel() == 0) {
            player.addMoney(property.mortgage());
            int mortgageValue = property.getMortgageValue();
            String text = player.getName() + "mortgaged " + property.getTileName() + "and get $" + mortgageValue;
            presenter.showMortgageProperty(player, property, text);
        } else {
            String text = player.getName() + "cannot mortgage " + property.getTileName();
            presenter.showMortgageProperty(player, property, text);
        }
    }

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     * @param presenter presenter which provides info for players.
     */
    @Override
    public void unmortgage(Player player, Property property, MortgagePropertyOutputBoundary presenter){
        int mortgageValue = 0;
        mortgageValue = property.getMortgageValue();
        mortgageValue = (int) (mortgageValue * 1.1);
        if (player.ownsProperty(property)) {
            player.subtractMoney(mortgageValue);
            property.unmortgage();
            String text = player.getName() + "unmortgaged " + property.getTileName() + "and subtract $" + Integer.toString(mortgageValue);
            presenter.showMortgageProperty(player, property, text);
        } else {
            String text = player.getName() + "cannot unmortgage " + property.getTileName();
            presenter.showMortgageProperty(player, property, text);
        }
    }
}
