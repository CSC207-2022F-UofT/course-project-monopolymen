package TurnUseCases.TradeUseCase;

import GameEntities.Tiles.Property;

import java.util.ArrayList;

/**
 * Details the options of what player1 and player2 can trade.
 */

public class TradeOption {

    // The amount of money player1 has.
    public int player1Money;

    // The amount of money player2 has.
    public int player2Money;

    // The properties player1 owns.
    public ArrayList<Property> player1Properties;

    // The properties player2 owns.
    public ArrayList<Property> player2Properties;

    /**
     * Creates a new TradeOption object.
     *
     * @param player1Money the amount of money player1 has.
     * @param player2Money the amount of money player2 has.
     * @param player1Properties the properties player1 owns.
     * @param player2Properties the properties player2 owns.
     */
    public TradeOption(int player1Money, int player2Money,
                       ArrayList<Property> player1Properties, ArrayList<Property> player2Properties ){
        this.player1Money = player1Money;
        this.player2Money = player2Money;
        this.player1Properties = player1Properties;
        this.player2Properties = player2Properties;
    }




}
