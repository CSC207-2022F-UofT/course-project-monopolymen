package turn_use_cases.build_use_case;

import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

public interface BuildBuildingInputBoundary {
    /**
     * Check if player can build buildings
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     */
    boolean isBuildable (Player player, Property property);

    /**
     * A list of properties on which can build a building.
     *
     * @param player the player who wants to build buildings.
     */
    void showBuildOption (Player player);

    /**
     * Build a house on the property.
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     */
    void buildHouse (Player player, ColorPropertyTile property);

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     */
    void buildHotel (Player player, ColorPropertyTile property);

    /**
     * Check if player can sell buildings
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    boolean isSellable(Player player, ColorPropertyTile property);

    /**
     * A list of properties which has building can be sold.
     *
     * @param player the player who wants to sell buildings.
     */
    void showSellOption (Player player);

    /**
     * Selling a building on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    void sellHouse(Player player, ColorPropertyTile property);

    /**
     * Selling a building on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    void sellHotel(Player player, ColorPropertyTile property);
}
