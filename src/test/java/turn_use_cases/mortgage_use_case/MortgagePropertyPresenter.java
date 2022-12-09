package turn_use_cases.mortgage_use_case;

import game_entities.Player;
import game_entities.tiles.Property;

import java.util.ArrayList;

public class MortgagePropertyPresenter implements MortgagePropertyOutputBoundary{
    /**
     * Presents the property which is mortgaged or unmortgaged by player.
     *
     * @param player the player who wants to mortgage or unmortgage property.
     * @param property The property which is mortgaged or unmortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    public void showMortgageProperty(Player player, Property property, String flavorText){}

    /**
     * Presents the property which is unmortgaged by player.
     *
     * @param player the player who wants to unmortgage property.
     * @param property The property which is unmortgaged by player.
     * @param flavorText the text describing what is happening.
     */
    public void showUnmortgageProperty(Player player, Property property, String flavorText){}

    /**
     * Presents the properties which can be mortgaged or unmortgaged.
     *
     * @param properties a list of properties which can be mortgaged or unmortgaged.
     * @param flavorText the text describing what is happening.
     */
    public void showMortgagePropertyList(ArrayList<Property> properties, String flavorText){}

    /**
     * Presents the properties which can be unmortgaged.
     *
     * @param properties a list of properties which can be unmortgaged.
     * @param flavorText the text describing what is happening.
     */
    public void showUnmortgagePropertyList(ArrayList<Property> properties, String flavorText){}

}
