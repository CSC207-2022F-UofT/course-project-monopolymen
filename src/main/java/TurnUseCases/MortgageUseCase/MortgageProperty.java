package TurnUseCases.MortgageUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;
import GameEntities.Tiles.RailroadTile;
import GameEntities.Tiles.UtilityTile;

/**
 * All methods related to mortgage.
 */
public class MortgageProperty implements MortgagePropertyInputBoundary{

    private MortgagePropertyOutputBoundary presenter;

    /**
     * Creates an instance of the MortgageProperty class with the provided presenter.
     *
     * @param presenter presenter which provides info for players.
     */
    public MortgageProperty(MortgagePropertyOutputBoundary presenter){
        this.presenter = presenter;
    }

    /**
     * Player mortgages a color property.
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    @Override
    public void mortgage(Player player, Property property){
        if (player.ownsProperty(property) && property.getHouse() == 0 && property.getHotel() == 0
        && property instanceof ColorPropertyTile) {
            player.addMoney(property.mortgage());
            int mortgageValue = property.getMortgageValue();
            String text = player.getName() + "mortgaged " + property.getTileName() + "and get $" + mortgageValue;
            presenter.showMortgageProperty(player, property, text);
        } else if (player.ownsProperty(property) && property instanceof RailroadTile
        || property instanceof UtilityTile){
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
     */
    @Override
    public void unmortgage(Player player, Property property){
        int unmortgageValue = property.getUnMortgageValue();
        if (player.ownsProperty(property)) {
            player.subtractMoney(unmortgageValue);
            property.unmortgage();
            String text = player.getName() + "unmortgaged " + property.getTileName() + "and subtract $" + Integer.toString(unmortgageValue);
            presenter.showMortgageProperty(player, property, text);
        } else {
            String text = player.getName() + "cannot unmortgage " + property.getTileName();
            presenter.showMortgageProperty(player, property, text);
        }
    }
}
