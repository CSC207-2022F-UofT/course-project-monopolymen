package turn_use_cases.move_player_use_case;

import game_entities.Player;
import game_entities.tiles.*;
import turn_interface_adapters.TurnController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the MovePlayerOutputBoundary interface.
 */
public class MovePlayerPresenter implements MovePlayerOutputBoundary {
    private final ArrayList<int[]> tilePositions;
    // Player offsets from eachother so all players can be seen on the board.
    private final int[][] playerOffset = {{0,0}, {20, 0}, {0, 20}, {20, 20}};
    private final JLayeredPane board;
    private final JPanel actionDialogBox;
    private ArrayList<JLabel> players;
    private final List<Player> playerList;
    private double scaleFactor;
    private int[][] scaledTilePositions;
    private final TurnController turnController;
    private final JPanel optionsWindow;

    public MovePlayerPresenter(JFrame mainWindow, JLayeredPane board, JPanel actionDialogBox, List<Player> playerList,
                               TurnController turnController, String tilePositionFilePath) {
        this.actionDialogBox = actionDialogBox;
        this.board = board;
        this.playerList = playerList;
        this.turnController = turnController;
        this.optionsWindow = new JPanel();
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
        board.add(boardImageLabel, Integer.valueOf(0));
        this.players = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            // draw a square with the image from assets
            ImageIcon playerImageScaled = new ImageIcon(new ImageIcon("src/main/resources/assets/misc/pieces/"
                    + playerList.get(i).getIcon() + ".png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            JLabel player = new JLabel(playerImageScaled);
            player.setPreferredSize(new Dimension(50, 50));
            player.setBounds(scaledTilePositions[playerList.get(i).getPosition()][0] + playerOffset[i][0],
                    scaledTilePositions[playerList.get(i).getPosition()][1]
                            + playerOffset[i][1], 50, 50);
            player.setLayout(new BorderLayout());
            board.add(player, Integer.valueOf(1));
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
        JButton otherOptions;
        if(player.getTurnsInJail() == -1) {
            otherOptions = new JButton(buttonText);
            otherOptions.addActionListener(e -> {
                // Temporary turn controller, gets the other options from the player and returns back to "main" action dialog panel
                turnController.endRollDice(rollAgain);
            });
        } else {
            otherOptions = new JButton("End Turn");
            otherOptions.addActionListener(e -> {
                // Temporary turn controller, gets the other options from the player and returns back to "main" action dialog panel
                turnController.endTurn();
            });
        }
        optionsWindow.add(otherOptions);
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
                .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        JLabel cardImageLabel = new JLabel(cardImage);
        // scale the image
        cardImageLabel.setPreferredSize(new Dimension(150, 150));
        optionsWindow.add(cardImageLabel);
        CardLayout cardLayout = (CardLayout) actionDialogBox.getLayout();
        cardLayout.show(actionDialogBox, "Roll options");
    }

    @Override
    public void showBuyableProperty(Player player, Tile tile, boolean buyable, boolean doubleRoll) {
        // Don't clear the options window, as this will be displayed regardless of the previous action
        Property property = (Property) tile;
        if(buyable) {
            JButton buyButton = new JButton("Buy " + property.getTileDisplayName() + " for $"
                    + property.getPurchasePrice());
            buyButton.addActionListener(e -> {
                // Temporary turn controller, calls the BuyPropertyUseCase and returns back to "main" action dialog panel
                turnController.buyProperty(property);
                turnController.endRollDice(doubleRoll);

            });
            optionsWindow.add(buyButton);
            // Show the property picture
            JLabel propertyImage = new JLabel();
            String frontOrBack = "front";
            String id;
            if (property instanceof UtilityTile){
                id = "utility_" + property.getTileName() + ".jpg";
            } else if (property instanceof RailroadTile) {
                id = "rr_" + property.getTileName() + ".jpg";
            } else {
                ColorPropertyTile newTemp = (ColorPropertyTile) property;
                id = newTemp.getColor().toLowerCase() + "_" + newTemp.getTileName() + ".jpg";
            }
            String path = "src/main/resources/assets/property/property_" + frontOrBack + "_" + id;
            System.out.println(path);
            ImageIcon temp = new ImageIcon(new ImageIcon
                    (path)
                    .getImage().getScaledInstance(150, 192, Image.SCALE_SMOOTH));
            propertyImage.setIcon(temp);
            propertyImage.setPreferredSize(new Dimension(150, 192));
            optionsWindow.add(propertyImage);
        }
    }
}