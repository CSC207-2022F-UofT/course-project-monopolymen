package TurnUseCases.BuildUseCase;

import GameEntities.Player;
import GameEntities.Tiles.ColorPropertyTile;

import java.util.ArrayList;

public interface BuildBuildingOutputBoundary {
    /**
     * Presents the property which would build a building.
     *
     * @param player player who wants to build a building.
     * @param property the property which would build a building.
     * @param flavorText the text describing what is happening.
     */
    public void showBuildBuilding(Player player, ColorPropertyTile property, String flavorText);

}
