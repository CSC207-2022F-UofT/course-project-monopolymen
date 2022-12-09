package turn_use_cases.mortgage_use_case;

import game_entities.Player;
import game_entities.tiles.Property;

/**
 * The input boundary for the mortgage property use case.
 */
public interface MortgagePropertyInputBoundary {

    /**
     * Provides a list of properties which can be mortgaged.
     *
     * @param player the player who wants mortgages property.
     */
    void showMortgageOption(Player player);

    /**
     * Provides a list of properties which can be unmortgaged.
     *
     * @param player the player who wants unmortgages property.
     */
    void showUnmortgageOption(Player player);

    /**
     * Overloading method mortgage. Player mortgages a color property.
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    void mortgage(Player player, Property property);

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     */
    void unmortgage(Player player, Property property);
}
