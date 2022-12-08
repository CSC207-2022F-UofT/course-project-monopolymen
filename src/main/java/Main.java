import game.LoadGameStateSerialize;
import ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new LoadGameStateSerialize("./saves/"));
        mainMenu.show();
    }
}