package TurnUseCases.MortgageUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;
/**
 * The output boundary for the mortgage property use case.
 */
public interface MortgagePropertyOutputBoundary {
    /**
     * Presents the property which is mortgaged or unmortgaged by player.
     *
     * @param player the player who wants to mortgage or unmortgage property.
     * @param property The property which is mortgaged or unmortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    public void showMortgageProperty(Player player, Property property, String flavorText);

    /**
     * Presents the property which is mortgaged or unmortgaged by player.
     *
     * @param player the player who wants to mortgage or unmortgage property.
     * @param property The property which is mortgaged or unmortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    public void showMortgageProperty(Player player, ColorPropertyTile property, String flavorText);
}
