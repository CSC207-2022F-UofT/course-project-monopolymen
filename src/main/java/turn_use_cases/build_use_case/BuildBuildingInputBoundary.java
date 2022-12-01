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
    public boolean isBuildable (Player player, Property property);

    /**
     * Build a house on the property.
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public void buildHouse (Player player, ColorPropertyTile property);

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public void buildHotel (Player player, ColorPropertyTile property);

    /**
     * Check if player can sell buildings
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    public boolean isSellable(Player player, Property property);

    /**
     * Selling a building on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    public void sellHouse(Player player, ColorPropertyTile property);

    /**
     * Selling a building on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     */
    public void sellHotel(Player player, ColorPropertyTile property);
}
