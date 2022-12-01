package TurnUseCases.MovePlayerUseCase;
import GameEntities.Board;
import GameEntities.Player;
import GameEntities.Tiles.GoTile;
import GameEntities.Tiles.GoToJailTile;
import GameEntities.Tiles.JailTile;
import GameEntities.Tiles.Tile;
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
