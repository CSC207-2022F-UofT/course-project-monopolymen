package turn_use_cases.trade_use_case;

import game_entities.tiles.Property;

import java.util.ArrayList;

/**
 * Details the options of what player1 and player2 can trade.
 */

public class TradeOption {

    // The amount of money player1 has.
    private int player1Money;

    // The amount of money player2 has.
    private int player2Money;

    private boolean player1JailCard;

    private boolean player2JailCard;

    // The properties player1 owns.
    private ArrayList<Property> player1Properties;

    // The properties player2 owns.
    private ArrayList<Property> player2Properties;

    /**
     * Creates a new TradeOption object.
     *
     * @param player1Money the amount of money player1 has.
     * @param player2Money the amount of money player2 has.
     * @param player1JailCard whether player1 has a get out of jail card.
     * @param player2JailCard whether player2 has a get out of jail card.
     * @param player1Properties the properties player1 owns.
     * @param player2Properties the properties player2 owns.
     */
    public TradeOption(int player1Money, int player2Money, boolean player1JailCard, boolean player2JailCard,
                       ArrayList<Property> player1Properties, ArrayList<Property> player2Properties ){
        this.player1Money = player1Money;
        this.player2Money = player2Money;
        this.player1JailCard = player1JailCard;
        this.player2JailCard = player2JailCard;
        this.player1Properties = player1Properties;
        this.player2Properties = player2Properties;
    }


    public int getPlayer1Money() {
        return player1Money;
    }

    public int getPlayer2Money() {
        return player2Money;
    }

    public boolean getPlayer1JailCard() {
        return player1JailCard;
    }

    public boolean getPlayer2JailCard() {
        return player2JailCard;
    }

    public ArrayList<Property> getPlayer1Properties() {
        return player1Properties;
    }

    public ArrayList<Property> getPlayer2Properties() {
        return player2Properties;
    }
}
