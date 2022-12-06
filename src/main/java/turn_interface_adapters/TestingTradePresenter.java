package turn_interface_adapters;

import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Tile;
import turn_use_cases.trade_use_case.TradeOffer;
import turn_use_cases.trade_use_case.TradeOption;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TestingTradePresenter {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("Monopoly Game");
        mainWindow.setSize(1366, 700); // TODO edit this if it's too big.
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Layout
        JPanel mainContainer = new JPanel();
        GridLayout splitPane = new GridLayout();
        mainContainer.setLayout(splitPane);
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // TODO I set the image here to be the path to the board image. You may not have it yet (go approve PR for the
        //  Graphic-assets), that should still run fine.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Left hand side
        JPanel splitLHS = new JPanel();
        ImageIcon boardImg = new ImageIcon("src/main/resources/assets/misc/board.jpg");
//        ImageIcon boardImg = new ImageIcon("C:\\Users\\Joshua\\Desktop\\school\\UToronto\\year2\\csc207\\monopoly_assets\\Recreated_by_me\\misc\\board.jpg");
        JLabel scaledBoardImg = new JLabel(new ImageIcon(boardImg.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT)));
        splitLHS.add(scaledBoardImg);


        // Right hand side (Split between top and bottom)
        JPanel splitRHS = new JPanel();
        GridLayout verticalSplit = new GridLayout(2, 1);
        splitRHS.setLayout(verticalSplit);
        // Action Dialog Box for top half of the RHS
        // This is where the use case presenters will interact with.




        // Misc box. Probably split into some sort of summary of current player information (ex. name, money)
        // that you click on to get to the viewInventory
        // and some place to view property title deed cards.
        JPanel bottomBox = new JPanel();
        JLabel bottomLabel = new JLabel("Placeholder");
        bottomBox.add(bottomLabel);

        JPanel actionDialogBoxes = new JPanel();
        CardLayout cardLayout = new CardLayout();
        actionDialogBoxes.setLayout(cardLayout);

        splitRHS.add(actionDialogBoxes);
        splitRHS.add(bottomBox);

        mainContainer.add(splitLHS);
        mainContainer.add(splitRHS);
        mainContainer.setVisible(true);
        mainWindow.add(mainContainer);


        mainWindow.setVisible(true);

        TradePresenter tradePresenter = new TradePresenter(actionDialogBoxes, cardLayout);

        ArrayList<Tile> tiles = new ArrayList<>();

        Board board = new Board(tiles);

        Player player1 = new Player("player1", "player1", 1500, board);

        Player player2 = new Player("player2", "player2", 1500, board);

        Player player3 = new Player("player3", "player3", 1500, board);

        Player player4 = new Player("player4", "player4", 1500, board);

        Player[] playerOfferedTrade = {player1};

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        int[] fakeRent = {};

        ColorPropertyTile fakeStreet = new ColorPropertyTile("red", "fakeStreet",
                "Fake Street", 500, fakeRent, 0, 0, 0);


        ColorPropertyTile otherRoad = new ColorPropertyTile("blue", "otherRoad",
                "Other Road", 500, fakeRent, 0, 0, 0);

        player1.addProperty(fakeStreet);
        player2.addProperty(otherRoad);

        TradeOption tradeOption = new TradeOption(player1.getMoney(), player2.getMoney(), player1.hasGetOutofJailFreeCard(),
                player2.hasGetOutofJailFreeCard(), player1.getProperties(), player2.getProperties(), player1, player2);

        TradeOffer tradeOffer = new TradeOffer(0, 0, player1.getProperties(), player2.getProperties(), player1, player2);

        tradePresenter.showTradeOffer(tradeOffer, "test");
        //tradePresenter.showTradeOptions(tradeOption, "");

        //tradePresenter.showListOfPlayers(players, player1, "Choose a Player!");


    }
}
