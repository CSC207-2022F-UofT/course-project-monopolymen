package TurnUseCases.MortgageUseCase;

import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;
/**
 * The output boundary for the mortgage property use case.
 */
public interface MortgagePropertyOutputBoundary {
    /**
     * Presents the list of players player can trade with.
     *
     * @param player the player who wants to mortgage or unmortgage property.
     * @param flavorText the text describing what is happening.
     */
    public void showPlayer(String player, String flavorText);

    /**
     * Presents the property which is mortgaged by player.
     *
     * @param property The property which is mortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    public void showMortgageProperty(Property property, String flavorText);

    /**
     * Presents Tthe property which is mortgaged by player.
     *
     * @param property The property which is mortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    public void showMortgageProperty(ColorPropertyTile property, String flavorText);

    /**
     * Presents the property which is unmortgaged by player.
     *
     * @param property details the possible options for the trade
     * @param flavorText the text describing what is happening.
     */
    public void showUnmortgageProperty(Property property, String flavorText);

    /**
     * Presents the property which is unmortgaged by player.
     *
     * @param property details the possible options for the trade
     * @param flavorText the text describing what is happening.
     */
    public void showUnmortgageProperty(ColorPropertyTile property, String flavorText);

}
