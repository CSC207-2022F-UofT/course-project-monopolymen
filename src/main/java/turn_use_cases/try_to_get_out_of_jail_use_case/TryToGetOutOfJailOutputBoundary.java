package turn_use_cases.try_to_get_out_of_jail_use_case;

import game_entities.Player;
import game_entities.tiles.Tile;
import game_entities.tiles.TilePassResultModel;

import java.util.ArrayList;

/**
 * Output Boundary for TryToGetOutOfJail UseCase, to be implemented by the Presenter
 */
public interface TryToGetOutOfJailOutputBoundary {

    /**
     * Shows the options the player can choose
     * @param options
     */
    public void showOptions(ArrayList<String> options);
}
