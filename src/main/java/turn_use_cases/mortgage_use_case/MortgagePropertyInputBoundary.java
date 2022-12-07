package turn_use_cases.mortgage_use_case;

import game_entities.Player;
import game_entities.tiles.Property;

import java.util.ArrayList;

/**
 * The input boundary for the mortgage property use case.
 */
public interface MortgagePropertyInputBoundary {

    /**
     * Provides a list of properties which can be mortgaged.
     *
     * @param player the player who wants mortgages property.
     */
    public void showMortgageOption(Player player);

    /**
     * Provides a list of properties which can be unmortgaged.
     *
     * @param player the player who wants unmortgages property.
     */
    public void showUnmortgageOption(Player player);

    /**
     * Overloading method mortgage. Player mortgages a color property.
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    public void mortgage(Player player, Property property);

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     */
    public void unmortgage(Player player, Property property);
}
