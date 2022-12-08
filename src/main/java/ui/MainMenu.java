package ui;

import game.Game;
import game.LoadGameState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu {
    private JFrame mainMenuWindow;
    private JPanel mainMenuContainer;
    private CardLayout mainMenuCards;
    private JPanel mainMenuPanel;
    private JPanel newGameMenu;
    private JPanel loadGameMenu;
    private Font size36;
    private Font size48;
    private Font size24;
    private final LoadGameState loadGameState;

    /**
     * Constructs the Main Menu for loading and making new games.
     */
    public MainMenu(LoadGameState loadGameState) {
        this.loadGameState = loadGameState;
        this.size24 = new Font("Serif", Font.PLAIN, 24);
        this.size36 = new Font("Serif", Font.PLAIN, 36);
        this.size48 = new Font("Serif", Font.PLAIN, 48);
        constructMainMenuWindow();
        constructMainMenuContainer();
        constructMainMenu();
        constructNewGameMenu();
        constructLoadGameMenu();
    }

    public void show() {
        mainMenuWindow.setVisible(true);
    }

    private void constructMainMenuWindow() {
        mainMenuWindow = new JFrame();
        mainMenuWindow.setSize(1920, 1080);
        mainMenuWindow.setLocationRelativeTo(null);
        mainMenuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuWindow.setTitle("Monopoly Game");
        mainMenuWindow.setIconImage(new ImageIcon("src/main/resources/assets/misc/monopoly_board_icon.png").getImage());
    }

    private void constructMainMenuContainer() {
        mainMenuContainer = new JPanel();
        mainMenuCards = new CardLayout();
        mainMenuContainer.setLayout(mainMenuCards);
        mainMenuWindow.add(mainMenuContainer);
    }

    private void constructMainMenu() {
        mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuContainer.add(mainMenuPanel, "mainMenuPanel");

        JPanel welcomeSplash = new JPanel(new GridBagLayout());
        mainMenuPanel.add(welcomeSplash, BorderLayout.CENTER);

        // Welcome Splash
        JLabel welcomeText = new JLabel("Welcome to Monopoly");
        welcomeText.setFont(new Font("Serif", Font.PLAIN, 86));
        welcomeSplash.add(welcomeText);
        GridBagConstraints secondRow = new GridBagConstraints();
        secondRow.gridy = 1;
        welcomeSplash.add(new JLabel("Created by Alireza, Cole, Joshua, Roger, Tahir, Yanbo, and Youssef"), secondRow);

        JPanel buttonChoices = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonChoices.setBorder(new EmptyBorder(0, 0, 50, 0));
        mainMenuPanel.add(buttonChoices, BorderLayout.SOUTH);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(size36);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuCards.show(mainMenuContainer, "newGame");
            }
        });
        buttonChoices.add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setFont(size36);
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuCards.show(mainMenuContainer, "loadGame");
            }
        });
        buttonChoices.add(loadGameButton);

        JButton quitGame = new JButton("Quit");
        quitGame.setFont(size36);
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonChoices.add(quitGame);
    }

    private void constructNewGameMenu() {
        newGameMenu = new JPanel(new BorderLayout());
        mainMenuContainer.add(newGameMenu, "newGame");

        JPanel titleCenterer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Create a new game of Monopoly");
        title.setFont(size48);
        titleCenterer.add(title);
        newGameMenu.add(titleCenterer, BorderLayout.NORTH);

        JPanel userInputPanel = new JPanel(new GridBagLayout());
        newGameMenu.add(userInputPanel, BorderLayout.CENTER);

        GridBagConstraints locationSpecifier = new GridBagConstraints();
        locationSpecifier.insets = new Insets(10, 10, 10, 10);
        locationSpecifier.gridy = 0;


        JPanel saveNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        userInputPanel.add(saveNamePanel, locationSpecifier);
        locationSpecifier.gridy++;

        JLabel gameNameInstructions = new JLabel("Enter a save name: ");
        gameNameInstructions.setFont(size36);
        saveNamePanel.add(gameNameInstructions);

        JTextField gameNameInput = new JTextField("My Game");
        gameNameInput.setFont(size24);
        gameNameInput.setPreferredSize(new Dimension(200, 50));
        saveNamePanel.add(gameNameInput);


        JPanel playerCreationPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        userInputPanel.add(playerCreationPanel, locationSpecifier);
        locationSpecifier.gridy++;

        playerCreationPanel.add(newPlayerPanel());
        playerCreationPanel.add(newPlayerPanel());

        JLabel warningText = new JLabel();
        warningText.setFont(size24);
        warningText.setForeground(new Color(255, 0, 0));
        userInputPanel.add(warningText, locationSpecifier);
        locationSpecifier.gridy++;

        JPanel playerCreationManagementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        userInputPanel.add(playerCreationManagementPanel, locationSpecifier);
        JButton addPlayerButton = new JButton("Add Player");
        addPlayerButton.setFont(size36);
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerCreationPanel.getComponents().length == 4) {
                    warningText.setText("4 Players Maximum");
                } else {
                    warningText.setText("");
                    playerCreationPanel.add(newPlayerPanel());
                    playerCreationPanel.revalidate();
                    playerCreationPanel.repaint();
                }
            }
        });
        playerCreationManagementPanel.add(addPlayerButton);
        JButton removePlayerButton = new JButton("Remove Player");
        removePlayerButton.setFont(size36);
        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerCreationPanel.getComponents().length == 2) {
                    warningText.setText("2 Players Minimum");
                } else {
                    warningText.setText("");
                    playerCreationPanel.remove(playerCreationPanel.getComponents().length - 1);
                    playerCreationPanel.revalidate();
                    playerCreationPanel.repaint();
                }
            }
        });
        playerCreationManagementPanel.add(removePlayerButton);


        JPanel submitArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        submitArea.setBorder(new EmptyBorder(0, 0, 50, 0));
        newGameMenu.add(submitArea, BorderLayout.SOUTH);
        JButton submitButton = new JButton("Start");
        submitButton.setFont(size36);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Game game = new Game(gameNameInput.getText());
                    for (Component playerComponent : playerCreationPanel.getComponents()) {
                        JPanel playerPanel = (JPanel) playerComponent;
                        JTextField playerName = (JTextField) playerPanel.getComponent(1);

                        // This method knows that the component is of type JComboBox<PlayerIcon> as the ComboBox is created
                        // in the newPlayerPanel() method in this class.
                        @SuppressWarnings("unchecked")
                        JComboBox<GameView.PlayerIcon> playerIcon = (JComboBox<GameView.PlayerIcon>) playerPanel.getComponent(3);
                        if (playerIcon.getSelectedItem() == null) {
                            // Unexpected behavior, a playerIcon should be selected at all times.
                            throw new RuntimeException("Player " + playerName + " has no selected playerIcon");
                        }
                        game.addPlayer(playerName.getText(), (GameView.PlayerIcon) playerIcon.getSelectedItem(), 1500);
                    }
                    game.startGame();
                    mainMenuWindow.setVisible(false);
                } catch (RuntimeException err) {
                    err.printStackTrace();
                    ErrorWindow.showErrorWindow(err.getMessage());
                }
            }
        });
        submitArea.add(submitButton);

        JButton returnButton = new JButton("Return");
        returnButton.setFont(size36);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuCards.show(mainMenuContainer, "mainMenuPanel");
            }
        });
        submitArea.add(returnButton);
    }

    private JPanel newPlayerPanel() {
        JPanel playerCreation = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel playerNameInstructions = new JLabel("Enter a name: ");
        playerNameInstructions.setFont(size36);
        playerCreation.add(playerNameInstructions);

        JTextField playerNameInput = new JTextField();
        playerNameInput.setFont(size24);
        playerNameInput.setPreferredSize(new Dimension(300, 50));
        playerCreation.add(playerNameInput);

        JLabel playerPieceInstructions = new JLabel("Select a Monopoly Piece: ");
        playerPieceInstructions.setFont(size36);
        playerCreation.add(playerPieceInstructions);

        JComboBox<GameView.PlayerIcon> playerIcons = new JComboBox<>();
        playerIcons.setFont(size24);
        for (GameView.PlayerIcon icon : GameView.PlayerIcon.values()) {
            playerIcons.addItem(icon);
        }
        playerIcons.setSelectedIndex(0);
        playerCreation.add(playerIcons);

        return playerCreation;
    }

    private void constructLoadGameMenu() {
        loadGameMenu = new JPanel(new GridBagLayout());
        mainMenuContainer.add(loadGameMenu, "loadGame");

        GridBagConstraints rowSpecifier = new GridBagConstraints();
        rowSpecifier.insets = new Insets(10, 10, 10, 10);
        rowSpecifier.gridy = 0;

        JLabel loadInstructions = new JLabel("Load Existing Game:");
        loadInstructions.setFont(size36);
        loadGameMenu.add(loadInstructions, rowSpecifier);
        rowSpecifier.gridy++;

        List<String> saveNames = loadGameState.getAllSaves();
        if (saveNames.size() == 0) {
            JLabel descriptionText = new JLabel("No saves exist. Try creating a new game first.");
            descriptionText.setFont(size36);
            loadGameMenu.add(descriptionText, rowSpecifier);
        } else {
            JLabel descriptionText = new JLabel("(Select a save)");
            descriptionText.setFont(size36);
            loadGameMenu.add(descriptionText, rowSpecifier);
            rowSpecifier.gridy++;

            JComboBox<String> saveOptions = new JComboBox<>();
            for (String save : saveNames) {
                saveOptions.addItem(save);
            }
            saveOptions.setSelectedIndex(0);
            saveOptions.setFont(size24);
            loadGameMenu.add(saveOptions, rowSpecifier);
            rowSpecifier.gridy++;

            JButton submitButton = new JButton("Submit");
            submitButton.setFont(size36);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Game game = new Game((String) saveOptions.getSelectedItem(), loadGameState.getSavesDirectory());
                        game.startGame();
                        mainMenuWindow.setVisible(false);
                    } catch (RuntimeException err) {
                        err.printStackTrace();
                        ErrorWindow.showErrorWindow(err.getMessage());
                    }
                }
            });
            loadGameMenu.add(submitButton, rowSpecifier);
            rowSpecifier.gridy++;
        }
        JButton returnButton = new JButton("Return");
        returnButton.setFont(size36);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuCards.show(mainMenuContainer, "mainMenuPanel");
            }
        });
        loadGameMenu.add(returnButton, rowSpecifier);
    }

    public JFrame getMainMenuWindow() {
        return mainMenuWindow;
    }

    public JPanel getMainMenuContainer() {
        return mainMenuContainer;
    }

    public JPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public JPanel getNewGameMenu() {
        return newGameMenu;
    }

    public JPanel getLoadGameMenu() {
        return loadGameMenu;
    }
}
