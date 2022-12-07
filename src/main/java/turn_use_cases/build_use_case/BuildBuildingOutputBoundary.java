package turn_use_cases.build_use_case;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

import java.util.ArrayList;

public interface BuildBuildingOutputBoundary {
    /**
     * Presents the list properties which would build a building.
     *
     * @param properties the property which would build a building or sell a building.
     * @param flavorText the text describing what is happening.
     */
    public void showBuildOption(ArrayList<Property> properties, String flavorText);

    /**
     * Presents the property which would build a building.
     *
     * @param player player who wants to build a building.
     * @param property the property which would build a building.
     * @param flavorText the text describing what is happening.
     */
    public void showBuildBuilding(Player player, ColorPropertyTile property, String flavorText);

    /**
     * Presents the property on which the building would be sold.
     *
     * @param player player who wants to sell a building.
     * @param property the property on which the building would be sold.
     * @param flavorText the text describing what is happening.
     */
    public void showSellBuilding(Player player, ColorPropertyTile property, String flavorText);

    /**
     * Presents the list properties which would sell a building.
     *
     * @param properties the property which would build a building or sell a building.
     * @param flavorText the text describing what is happening.
     */
    public void showSellOption(ArrayList<Property> properties, String flavorText);

}
