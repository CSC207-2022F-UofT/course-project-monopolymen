package turn_use_cases.move_player_use_case;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import game_entities.Player;
import game_entities.tiles.Tile;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.RailroadTile;
import game_entities.tiles.TilePassResultModel;
import game_entities.tiles.UtilityTile;
import turn_interface_adapters.TurnController;

import javax.swing.*;

/**
 * Implementation of the MovePlayerOutputBoundary interface.
 */
public class MovePlayerPresenter implements MovePlayerOutputBoundary {
    private ArrayList<int[]> tilePositions;
    // Player offsets from eachother so all players can be seen on the board.
    private int[][] playerOffset = {{0,0}, {10, 0}, {0, 10}, {10, 10}};
    private JLayeredPane board;
    private JPanel actionDialogBox;
    private ArrayList<JLabel> players;
    private List<Player> playerList;
    private double scaleFactor;
    private int[][] scaledTilePositions;
    private TurnController turnController;
    private JPanel optionsWindow;
    private JFrame mainWindow;

    public MovePlayerPresenter(JFrame mainWindow, JLayeredPane board, JPanel actionDialogBox, List<Player> playerList,
                               TurnController turnController, String tilePositionFilePath) {
        this.actionDialogBox = actionDialogBox;
        this.board = board;
        this.playerList = playerList;
        this.turnController = turnController;
        this.optionsWindow = new JPanel();
        this.mainWindow = mainWindow;
        // read in the tile positions from TilePositions.txt
        this.tilePositions = new ArrayList<>();
        actionDialogBox.add(optionsWindow, "Roll options");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tilePositionFilePath)); //FilePath here
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                int[] tilePosition = {Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1])};
                this.tilePositions.add(tilePosition);
            }
        } catch (Exception e) {
            System.out.println("Error reading TilePositions.txt");
            throw new RuntimeException(e);
        }
        this.scaledTilePositions = scaleTilePositions();
        rescale(mainWindow.getWidth());
        populateBoard();
        // Resize the board image on window resizing.
        mainWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                rescale(mainWindow.getWidth());
            }
        });
    }

    private void rescale(int newWidth) {
        // scaleFactor is half of the newWidth in 100s of pixels over 15.
        // newWidth has 100 subtracted to give a 50 pixel buffer between the right edge of the board
        // and the halfway split mark.
        this.scaleFactor = ((newWidth - 100) / 200.0) / 15.0;
        this.scaledTilePositions = scaleTilePositions();
        board.removeAll();
        populateBoard();
        board.validate();
        board.repaint();
    }

    /**
     * Creates the board and the players.
     * Does not create "roll dice" button as it is handled by the controller.
     */
    private void populateBoard() {
        // draw the board
        ImageIcon boardImage = new ImageIcon(new ImageIcon("src/main/resources/assets/misc/board.jpg")
                .getImage().getScaledInstance((int)(1500 * scaleFactor), (int)(1500 * scaleFactor), Image.SCALE_SMOOTH));
        JLabel boardImageLabel = new JLabel(boardImage);
        boardImageLabel.setBounds(0, 0, (int)(1500 * scaleFactor), (int)(1500 * scaleFactor));
        board.add(boardImageLabel, new Integer(0));
        this.players = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            // draw a square with the image from assets
            ImageIcon playerImageScaled = new ImageIcon(new ImageIcon("src/main/resources/assets/misc/pieces/"
                    + playerList.get(i).getIcon() + ".png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            JLabel player = new JLabel(playerImageScaled);
            player.setPreferredSize(new Dimension(50, 50));
            player.setBounds(scaledTilePositions[0][0] + playerOffset[i][0], scaledTilePositions[0][1]
                    + playerOffset[i][1], 50, 50);
            player.setLayout(new BorderLayout());
            board.add(player,new Integer(1));
            players.add(player);
        }
    }

    /**
     * Scales the tile positions to the size of the board.
     * @return the scaled tile positions.
     */
    private int[][] scaleTilePositions() {
        int[][] scaledTilePositions = new int[tilePositions.size()][2];
        for (int i = 0; i < tilePositions.size(); i++) {
            scaledTilePositions[i][0] = (int) (tilePositions.get(i)[0] * scaleFactor);
            scaledTilePositions[i][1] = (int) (tilePositions.get(i)[1] * scaleFactor);
        }
        return scaledTilePositions;
    }

    @Override
    public void showResultOfAction(Player player, int playerPosition, boolean rollAgain, String flavorText,
                                   String buttonText) {
        // Clear the options window.
        // Show the flavor text.
        JLabel flavorTextLabel = new JLabel(flavorText);
        optionsWindow.add(flavorTextLabel);
        // Move the player to the new position.
        JLabel playerPanel = players.get(playerList.indexOf(player));
        // Move the player to the new position.
        playerPanel.setBounds(scaledTilePositions[playerPosition][0] + playerOffset[playerList.indexOf(player)][0],
                scaledTilePositions[playerPosition][1] + playerOffset[playerList.indexOf(player)][1], 50, 50);
        if(player.getTurnsInJail() == -1) {
            JButton otherOptions = new JButton(buttonText);
            otherOptions.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Temporary turn controller, gets the other options from the player and returns back to "main" action dialog panel
                    turnController.endRollDice(rollAgain);
                }
            });
            optionsWindow.add(otherOptions);
        } else {
            JButton otherOptions = new JButton("End Turn");
            otherOptions.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Temporary turn controller, gets the other options from the player and returns back to "main" action dialog panel
                    turnController.endTurn();
                }
            });
            optionsWindow.add(otherOptions);
        }
        CardLayout cardLayout = (CardLayout) actionDialogBox.getLayout();
        cardLayout.show(actionDialogBox, "Roll options");
    }
    
    @Override
    public void showResultOfPass(Player player, int playerPosition, TilePassResultModel tilePassResultModel) {
        // Clear the options window.
        JLabel playerPanel = players.get(playerList.indexOf(player));
        // Move the player to the new position.
        playerPanel.setBounds(scaledTilePositions[playerPosition][0] + playerOffset[playerList.indexOf(player)][0],
                scaledTilePositions[playerPosition][1] + playerOffset[playerList.indexOf(player)][1], 50, 50);
        optionsWindow.add(new JLabel(tilePassResultModel.getFlavorText()));
    }

    @Override
    public void showRoll(int[] playerRollAmount){
        // Clear the options window.
        optionsWindow.removeAll();
        optionsWindow.add(new JLabel("You rolled a " + playerRollAmount[0] + " and a " + playerRollAmount[1]));
        CardLayout cardLayout = (CardLayout) actionDialogBox.getLayout();
        cardLayout.show(actionDialogBox, "Roll options");
    }

    @Override
    public void showCardDraw(Player player, String cardName, boolean rollAgain,  boolean isChance) {
        // Clear the options window. as this is different from showResultOfAction
        ImageIcon cardImage = new ImageIcon(new ImageIcon("src/main/resources/assets/cards/" + cardName + ".jpg")
                .getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        JLabel cardImageLabel = new JLabel(cardImage);
        // scale the image
        cardImageLabel.setPreferredSize(new Dimension(300, 300)); 
        optionsWindow.add(cardImageLabel);
        CardLayout cardLayout = (CardLayout) actionDialogBox.getLayout();
        cardLayout.show(actionDialogBox, "Roll options");
    }

    @Override
    public void showBuyableProperty(Player player, Tile tile, boolean buyable, boolean doubleRoll) {
        // Don't clear the options window, as this will be displayed regardless of the previous action
        Property property = (Property) tile;
        if(buyable) {
            JButton buyButton = new JButton("Buy " + property.getTileName() + " for $"
                    + property.getPurchasePrice());
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Temporary turn controller, calls the BuyPropertyUseCase and returns back to "main" action dialog panel
                    turnController.buyProperty(property);
                    turnController.endRollDice(doubleRoll);

                }
            });
            optionsWindow.add(buyButton);
            // Show the property picture
            JLabel propertyImage = new JLabel();
            String frontOrBack = "front";
            String id = "";
            if (property instanceof UtilityTile){
                id = "utility_" + property.getTileDisplayName() +".jpg";
            } else if (property instanceof RailroadTile) {
                id = "rr_" + property.getTileDisplayName() + ".jpg";
            } else {
                ColorPropertyTile newTemp = (ColorPropertyTile) property;
                id = newTemp.getColor().toLowerCase() + "_" + newTemp.getTileDisplayName() + ".jpg";
            }
            String path = "src/main/resources/assets/property/property_" + frontOrBack + "_" + id;
            System.out.println(path);
            ImageIcon temp = new ImageIcon(new ImageIcon
                    (path)
                    .getImage().getScaledInstance((int) (250), (int) (320), Image.SCALE_SMOOTH));
            propertyImage.setIcon(temp);
            propertyImage.setPreferredSize(new Dimension(250, 320));
            optionsWindow.add(propertyImage);
        }
    }
}