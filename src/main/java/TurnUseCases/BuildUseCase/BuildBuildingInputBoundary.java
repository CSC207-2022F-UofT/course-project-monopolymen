package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

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
     * @param presenter presenter which provides info for players
     */
    public void buildHouse (Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter);

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants to build buildings.
     * @param property the property on which player wants to build buildings.
     * @param presenter presenter which provides info for players
     */
    public void buildHotel (Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter);

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
     * @param presenter presenter which provides info for players
     */
    public void sellHouse(Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter);

    /**
     * Selling a building on the property.
     *
     * @param player the player who wants to sell buildings.
     * @param property the property on which player wants to sell buildings.
     * @param presenter presenter which provides info for players
     */
    public void sellHotel(Player player, ColorPropertyTile property, BuildBuildingOutputBoundary presenter);
}
