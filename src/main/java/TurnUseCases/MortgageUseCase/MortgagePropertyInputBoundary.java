package TurnUseCases.MortgageUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;
/**
 * The input boundary for the mortgage property use case.
 */
public interface MortgagePropertyInputBoundary {
    /**
     * Player mortgages property
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    public void mortgage(Player player, Property property, MortgagePropertyOutputBoundary presenter);

    /**
     * Overloading method mortgage. Player mortgages a color property.
     *
     * @param player the player who wants mortgages property.
     * @param property the property which player wants to mortgages.
     */
    public void mortgage(Player player, ColorPropertyTile property, MortgagePropertyOutputBoundary presenter);

    /**
     * Player unmortgages property
     *
     * @param player the player who wants to unmortgages property.
     * @param property the property which player wants to unmortgages.
     */
    public void unmortgage(Player player, Property property, MortgagePropertyOutputBoundary presenter);
}
