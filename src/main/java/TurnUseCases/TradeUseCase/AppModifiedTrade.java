package TurnUseCases.TradeUseCase;


import game_entities.Board;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;
import game_entities.tiles.Tile;
import turn_use_cases.trade_use_case.TradeOffer;
import turn_use_cases.trade_use_case.TradeOption;
import turn_use_cases.trade_use_case.TradeOutputBoundary;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class AppModifiedTrade extends JPanel implements TradeOutputBoundary, PropertyChangeListener {
    static JTextArea output;
    String newline = "\n";

    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();

    }


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
        JPanel actionDialogBoxes = new JPanel();
        CardLayout cardLayout = new CardLayout();
        actionDialogBoxes.setLayout(cardLayout);

        // Misc box. Probably split into some sort of summary of current player information (ex. name, money)
        // that you click on to get to the viewInventory
        // and some place to view property title deed cards.
        JPanel bottomBox = new JPanel();
        JLabel bottomLabel = new JLabel("Placeholder");
        bottomBox.add(bottomLabel);

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

        TradeOffer[] tradeOffer = new TradeOffer[1];
        int[] jailCard = {0};

        /*
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            NOTE this process will likely be abstracted to some dialogBox class that your presenter methods
            will create.

            Example:
            firstWindow is the first card we add simulating the first popup at the beginning of the turn
            that asks what turnAction you would like to take
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        */
        JPanel firstWindow = new JPanel();
        firstWindow.setLayout(new BoxLayout(firstWindow, BoxLayout.X_AXIS));
        firstWindow.setBackground(new Color(255, 255, 255));
        firstWindow.add(new JLabel("Explanation Text Here. Pick the action you would like to take"));
        JButton demoMultiStageUseCase = new JButton("Trade");
        demoMultiStageUseCase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Simulating calling a controller method");
                System.out.println("Controller method calls a use case");
                System.out.println("Use case does something");
                bottomLabel.setText("Something happened when you called the use case");
                System.out.println("Use Case calls it's presenter to present a choice");

                // Presenter code. Pretend int array represents list of choices

                JPanel optionsWindow = new JPanel();
                optionsWindow.add(new JLabel("Choose an option"));
                for (Player player : players) {
                    JButton option = new JButton("Pick " + player.getName());
                    option.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // after whatever happens, controller goes back to the turnAction choices screen
                            playerOfferedTrade[0] = player;




                            CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                            cl.show(actionDialogBoxes, "trade2");
                        }
                    });
                    optionsWindow.add(option);
                }
                actionDialogBoxes.add(optionsWindow, "optionWindow");
                CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                cl.show(actionDialogBoxes, "optionWindow");
            }
        });
        firstWindow.add(demoMultiStageUseCase);

        JButton trade2 = new JButton("Trade 2");
        trade2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> player1PropertiesDisplay = new ArrayList<>();
                ArrayList<Property> player1Properties = new ArrayList<>();

                for (Property property : player1.getProperties()){
                    player1PropertiesDisplay.add(property.getTileDisplayName());
                    player1Properties.add(property);
                }



                ArrayList<String> player2PropertiesDisplay = new ArrayList<>();
                ArrayList<Property> player2Properties = new ArrayList<>();

                for (Property property : playerOfferedTrade[0].getProperties()){
                    player2PropertiesDisplay.add(property.getTileDisplayName());
                    player2Properties.add(property);
                }

                ArrayList<Property> propertiesOffered = new ArrayList<>();
                ArrayList<Property> propertiesReceived= new ArrayList<>();



                JList listP1Properties = new JList<>(player1PropertiesDisplay.toArray());
                listP1Properties.setVisible(true);
                listP1Properties.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                JScrollPane listPane1 = new JScrollPane(listP1Properties);

                JList listP2Properties = new JList<>(player2PropertiesDisplay.toArray());
                listP2Properties.setVisible(true);
                listP2Properties.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                JScrollPane listPane2 = new JScrollPane(listP2Properties);



                JPanel listContainer = new JPanel(new GridLayout(1,6));
                listContainer.setBorder(BorderFactory.createTitledBorder(
                        "List"));
                listContainer.add(listPane1);

                JLabel text1 = new JLabel();
                text1.setText("Prop. Offered");

                listContainer.add(text1);

                listContainer.add(listPane2);

                JLabel text2 = new JLabel();
                text2.setText("Prop. Requested");

                listContainer.add(text2);

                int tradeMoney = 0;
                JFormattedTextField tradeMoneyField = new JFormattedTextField(NumberFormat.getNumberInstance());
                tradeMoneyField.setEditable(true);
                tradeMoneyField.setValue(tradeMoney);
                tradeMoneyField.setColumns(15);

                listContainer.add(tradeMoneyField);

                JRadioButton noJailCard = new JRadioButton("No JC");
                JRadioButton offerJailCard = new JRadioButton("Offer JC");
                JRadioButton requestJailCard = new JRadioButton("Request JC");

                ButtonGroup buttonGroup = new ButtonGroup();
                buttonGroup.add(noJailCard);
                buttonGroup.add(offerJailCard);
                buttonGroup.add(requestJailCard);

                JPanel jailButtons = new JPanel();
                jailButtons.setLayout(new BoxLayout(jailButtons,BoxLayout.Y_AXIS));
                jailButtons.setSize(1,3);
                jailButtons.add(noJailCard);
                jailButtons.add(offerJailCard);
                jailButtons.add(requestJailCard);
                listContainer.add(jailButtons);



                noJailCard.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jailCard[0] = 0;
                    }
                });

                offerJailCard.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jailCard[0] = 1;
                    }
                });

                requestJailCard.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jailCard[0] = -1;
                    }
                });



                JButton submit = new JButton("Submit Offer");

                listContainer.add(submit);

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Submitted Trade Offer!");


                        TradeOffer tradeOffer1 = new TradeOffer(tradeMoney, jailCard[0],
                                propertiesOffered, propertiesReceived, player1, playerOfferedTrade[0]);
                        tradeOffer[0] = tradeOffer1;



                        if (tradeOffer1.isValid()) {
                            CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                            cl.show(actionDialogBoxes, "trade3");
                        }
                        else {
                            System.out.println("That was invalid, please try again.");
                        }
                    }
                });



                actionDialogBoxes.add(listContainer, "trade2");
                CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                cl.show(actionDialogBoxes, "trade2");

                listP1Properties.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String propertyText = "";
                        propertiesOffered.clear();
                        List<String> SelectedProperties = listP1Properties.getSelectedValuesList();
                        for (String propString: SelectedProperties){
                            propertyText = propertyText + propString + ", ";
                            int x = player1PropertiesDisplay.indexOf(propString);
                            propertiesOffered.add(player1Properties.get(x));
                        }
                        propertyText = propertyText.substring(0, propertyText.length()-2);
                        text1.setText(propertyText);



                    }
                });

                listP2Properties.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String propertyText = "";
                        propertiesReceived.clear();
                        List<String> SelectedProperties = listP2Properties.getSelectedValuesList();
                        for (String propString: SelectedProperties){
                            propertyText = propertyText + propString + ", ";
                            int x = player2PropertiesDisplay.indexOf(propString);
                            propertiesReceived.add(player2Properties.get(x));
                        }
                        propertyText = propertyText.substring(0, propertyText.length()-2);
                        text2.setText(propertyText);


                    }
                });




            }
        });

        JButton trade3 = new JButton("Trade 3");
        trade3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel showTradeOffer = new JPanel();
                actionDialogBoxes.add(showTradeOffer, "trade3");
                CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                cl.show(actionDialogBoxes, "trade3");


                if (!tradeOffer[0].isValid()){
                    System.out.println(tradeOffer[0].getJailCard());
                    showTradeOffer.add(new JLabel("That offer was invalid, please try again."));
                    JButton newOffer = new JButton("New Offer");
                    showTradeOffer.add(newOffer);
                    newOffer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                            cl.show(actionDialogBoxes, "trade2");
                        }
                    });
                }
                else {
                    showTradeOffer.add(new JLabel("Add trade offer here:"));
                    JButton acceptOffer = new JButton("Accept Offer");
                    acceptOffer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("You accepted the Trade Offer!");
                        }
                    });

                    JButton declineOffer = new JButton("Decline Offer");
                    declineOffer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("You declined the Trade Offer!");
                        }
                    });

                    JButton counterOffer = new JButton("Counter Offer");
                    counterOffer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("You decided to make a counter offer!");

                        }
                    });

                    showTradeOffer.add(acceptOffer);
                    showTradeOffer.add(declineOffer);
                    showTradeOffer.add(counterOffer);
                }


            }
        });

















        actionDialogBoxes.add(firstWindow, "firstWindow");
        actionDialogBoxes.add(trade2, "trade2");
        actionDialogBoxes.add(trade3, "trade3");
        cardLayout.show(actionDialogBoxes, "firstWindow");

        /*
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            End of actionDialogBoxes section
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         */

        splitRHS.add(actionDialogBoxes);
        splitRHS.add(bottomBox);


        mainContainer.add(splitLHS);
        mainContainer.add(splitRHS);
        mainContainer.setVisible(true);
        mainWindow.add(mainContainer);


        mainWindow.setVisible(true);
    }


    /**
     * Presents the list of players player can trade with.
     *
     * @param listOfPlayers the list of players player can trade with.
     * @param flavorText    the text describing what is happening.
     */
    @Override
    public void showListOfPlayers(ArrayList<Player> listOfPlayers, Player player, String flavorText) {



    }

    /**
     * Presents the possible options for player1 to trade with player2.
     *
     * @param tradeOption details the possible options for the trade
     * @param flavorText  the text describing what is happening.
     */
    @Override
    public void showTradeOptions(TradeOption tradeOption, String flavorText) {

    }

    /**
     * Presents the trade offer.
     *
     * @param tradeOffer details what would happen during the trade.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showTradeOffer(TradeOffer tradeOffer, String flavorText) {

    }

    /**
     * Presents the result of the trade offer.
     *
     * @param option     the number indicating the result of the trade.
     *                   *               1 - player2 accepted the trade offer.
     *                   *               2 - player2 decided to make a counter offer.
     *                   *               3 - player2 declined the trade offer.
     *                   *               4 - player2's input was not valid and needs to try again.
     * @param flavorText the text describing what is happening.
     */
    @Override
    public void showResultOfTradeOffer(int option, String flavorText) {

    }



    class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();

            int firstIndex = e.getFirstIndex();
            int lastIndex = e.getLastIndex();
            boolean isAdjusting = e.getValueIsAdjusting();
            output.append("Event for indexes "
                    + firstIndex + " - " + lastIndex
                    + "; isAdjusting is " + isAdjusting
                    + "; selected indexes:");

            if (lsm.isSelectionEmpty()) {
                output.append(" <none>");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        output.append(" " + i);
                    }
                }
            }
            output.append(newline);
            output.setCaretPosition(output.getDocument().getLength());
        }
    }




}

