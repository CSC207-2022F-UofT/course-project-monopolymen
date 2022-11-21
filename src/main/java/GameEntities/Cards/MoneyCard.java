package GameEntities.Cards;

import GameEntities.Player;

public class MoneyCard extends Card{

    private int money; //This number can be positive or negative depending on whether you gain
    //or lose money
    protected MoneyCard(String cardName, String cardDisplayName, String flavourText, boolean ownable,
                        boolean chanceCard, int money) {
        super(cardName, cardDisplayName, flavourText, chanceCard);

        this.money = money;
    }


    @Override
    public void cardAction(Player player) {
        if(0 <= this.money) {
            player.addMoney(this.money);
        }
        else {
            player.subtractMoney(this.money);
        }

    }
}
