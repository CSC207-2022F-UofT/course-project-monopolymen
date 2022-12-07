package turn_interface_adapters;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.Property;
import turn_use_cases.mortgage_use_case.MortgagePropertyOutputBoundary;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MortgagePropertyPresenter implements MortgagePropertyOutputBoundary {

    private final JPanel turnActionsPanel;
    private final JPanel turnActionButtonsPanel;
    private final TurnController controller;



    public MortgagePropertyPresenter(JPanel actionDialogBoxes, Player player, TurnController controller) {
        this.controller = controller;

        turnActionsPanel = new JPanel();
        turnActionsPanel.setLayout(new BorderLayout());
        turnActionButtonsPanel = new JPanel();
        turnActionButtonsPanel.setLayout(new GridLayout());
        turnActionsPanel.add(turnActionButtonsPanel, BorderLayout.SOUTH);

    }

    @Override
    public void showMortgagePropertyList(ArrayList<Property> properties, String flavorText) {
        resetTurnActionsPanel();
        turnActionsPanel.add(new JLabel("This is a list of properties you can mortgage."), BorderLayout.CENTER);
        JButton optionsButton = new JButton("Check");
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(flavorText);
            }
        });
        turnActionButtonsPanel.add(optionsButton);
        showTurnActionsPanel();
    }

    @Override
    public void showUnmortgagePropertyList(ArrayList<Property> properties, String flavorText) {
        resetTurnActionsPanel();
        turnActionsPanel.add(new JLabel("This is a list of properties you can unmortgage."), BorderLayout.CENTER);
        JButton optionsButton = new JButton("Check");
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(flavorText);
            }
        });
        turnActionButtonsPanel.add(optionsButton);
        showTurnActionsPanel();
    }

    @Override
    public void showMortgageProperty(Player player, Property property, String flavorText) {
        resetTurnActionsPanel();
        turnActionsPanel.add(new JLabel("Mortgage " + property), BorderLayout.CENTER);
        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(flavorText);
                controller.mortgageProperty(property);
            }
        });
        turnActionButtonsPanel.add(yesButton);
        showTurnActionsPanel();

        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        turnActionButtonsPanel.add(noButton);
        showTurnActionsPanel();

    }

    @Override
    public void showUnmortgageProperty(Player player, Property property, String flavorText) {
        resetTurnActionsPanel();
        turnActionsPanel.add(new JLabel("Unmortgage " + property), BorderLayout.CENTER);
        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(flavorText);
                controller.unmortgageProperty(property);
            }
        });
        turnActionButtonsPanel.add(yesButton);
        showTurnActionsPanel();

        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        turnActionButtonsPanel.add(noButton);
        showTurnActionsPanel();

    }

    private void resetTurnActionsPanel() {
        turnActionsPanel.removeAll();
        turnActionButtonsPanel.removeAll();
        turnActionsPanel.add(turnActionButtonsPanel, BorderLayout.SOUTH);
    }

    private void showTurnActionsPanel() {
        turnActionsPanel.revalidate();
        turnActionsPanel.repaint();
        turnActionButtonsPanel.revalidate();
        turnActionButtonsPanel.repaint();
    }

}
