import game.Game;
import game_entities.tiles.Property;
import ui.GameView.PlayerIcon;

public class Main {
    public static void main(String[] args) {
        Game game = new Game("Sample Game");

        game.addPlayer("Player1", PlayerIcon.BATTLESHIP, 1500);
        game.addPlayer("player2", PlayerIcon.CAR, 1500);
        game.addPlayer("player3", PlayerIcon.THIMBLE, 1500);
        game.addPlayer("player4", PlayerIcon.HAT, 1500);

        // Load game code
//        Game game = new Game("Sample Game", "./saves/");

        game.startGame();
        for (Property prop : game.getBoard().getPropertyTiles()) {
            prop.setOwner(game.getPlayers().get(0));
            game.getPlayers().get(0).addProperty(prop);
        }
    }
}