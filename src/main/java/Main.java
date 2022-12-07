import game.Game;
import ui.GameView.PlayerIcon;

public class Main {
    public static void main(String[] args) {
        Game game = new Game("Sample Game");

        game.addPlayer("Player1", PlayerIcon.BATTLESHIP, 1500);
        game.addPlayer("player2", PlayerIcon.CAR, 1500);
        game.addPlayer("player3", PlayerIcon.THIMBLE, 1500);
        game.addPlayer("player4", PlayerIcon.HAT, 1500);

        game.startGame();
    }
}