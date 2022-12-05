package TurnUseCases.TradeUseCase;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
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
        JButton demoMultiStageUseCase = new JButton("Demo a multi-stage use case with choices");
        demoMultiStageUseCase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Simulating calling a controller method");
                System.out.println("Controller method calls a use case");
                System.out.println("Use case does something");
                bottomLabel.setText("Something happened when you called the use case");
                System.out.println("Use Case calls it's presenter to present a choice");

                // Presenter code. Pretend int array represents list of choices
                int[] choices = {0, 1, 2, 3};

                JPanel optionsWindow = new JPanel();
                optionsWindow.add(new JLabel("Choose an option"));
                for (int x : choices) {
                    JButton option = new JButton("Pick " + x);
                    option.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Button talked to controller method #" + x);
                            // after whatever happens, controller goes back to the turnAction choices screen
                            CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();
                            cl.show(actionDialogBoxes, "firstWindow");
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

        // Use case launching a popup window with choices
        // If we launch a new popup, we better add an empty card or a card with no choices to the original window
        // so they can't pick multiple choices at once
        JPanel blankChoice = new JPanel();
        blankChoice.add(new JLabel("Nothing here to do while use case popup window does it's thing"));
        actionDialogBoxes.add(blankChoice, "blankChoice");

        JButton demoUseCaseLaunchingNewPopup = new JButton("Launch a new window for something");
        demoUseCaseLaunchingNewPopup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("New popup opening");
                //Adding to JFrame to create new window.
                JFrame newPopup = new JFrame("Popup window");
                JPanel optionPane = new JPanel();
                optionPane.add(new JLabel("Add things here maybe? (close this window with the x as I haven't added buttons)"));
                newPopup.add(optionPane);
                newPopup.pack(); // Sets size dynamically to content.
                newPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this popup only.
                newPopup.setVisible(true);

                CardLayout cl = (CardLayout) actionDialogBoxes.getLayout();

                // Maybe do something when the window is closed?
                // In this case shows the first window again
                newPopup.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        cl.show(actionDialogBoxes, "firstWindow");
                    }
                });

                // clear out the dialog box so they can't pick multiple things in a row.
                cl.show(actionDialogBoxes, "blankChoice");
            }
        });
        firstWindow.add(demoUseCaseLaunchingNewPopup);


        actionDialogBoxes.add(firstWindow, "firstWindow");
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
}

