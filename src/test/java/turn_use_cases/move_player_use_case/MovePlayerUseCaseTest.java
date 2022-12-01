package turn_use_cases.move_player_use_case;
import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.GoTile;
import game_entities.tiles.GoToJailTile;
import game_entities.tiles.JailTile;
import game_entities.tiles.Tile;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

public class MovePlayerUseCaseTest {
    Tile go = new GoTile();
    Tile jail = new JailTile();
    Tile goToJail = new GoToJailTile();
    ArrayList<Tile> temp1 = new ArrayList<Tile>(){{
        add(go);
        add(jail);
        add(goToJail);
    }};
    Board board = new Board(temp1);
    Player testPlayer = new Player("Dummy", "dog", 1500, board);


    @Test
    public void testPlayerMove() {

    }
}
