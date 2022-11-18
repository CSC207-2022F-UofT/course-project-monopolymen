package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;
import GameEntities.Tiles.Property;

public interface BuildBuildingInputBoundary {
    /**
     * Check if player can build buildings
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public boolean isBuildable (Player player, Property property);

    /**
     * Build a house on the property.
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public void buildHouse (Player player, ColorPropertyTile property);

    /**
     * Build a hotel on the property.
     *
     * @param player the player who wants build buildings.
     * @param property the property on which player wants to build buildings.
     */
    public void buildHotel (Player player, ColorPropertyTile property);
}
