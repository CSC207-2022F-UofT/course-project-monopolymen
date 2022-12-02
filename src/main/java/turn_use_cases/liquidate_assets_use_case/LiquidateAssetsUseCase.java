package turn_use_cases.liquidate_assets_use_case;

import game.GameState;
import game_entities.Player;
import game_entities.tiles.ColorPropertyTile;
import game_entities.tiles.Property;

import java.util.ArrayList;

public class LiquidateAssetsUseCase implements LiquidateAssetsInputBoundary{
    private final LiquidateAssetsOutputBoundary presenter;

    public LiquidateAssetsUseCase(LiquidateAssetsOutputBoundary presenter){
        this.presenter = presenter;
    }

    @Override
    public void getPlayerOptions(Player player, int moneyOwed) {
        ArrayList<String> playerOptions = new ArrayList<>();
        if(moneyOwed <= player.getMoney()){
            playerOptions.add("Pay Money");
            presenter.showPlayerOptions(playerOptions, player);
        }
        //You can only trade if you have properties to trade with.
        if(0 < player.getProperties().size()){
            playerOptions.add("Trade");
        }
        for(int i = 0; i < player.getProperties().size(); i++){
            if (!player.getProperties().get(i).isMortgaged()) {
                playerOptions.add("Mortgage Property");
                break;
            }
        }
        for(int i = 0; i < player.getProperties().size(); i++){
            if(player.getProperties().get(i) instanceof ColorPropertyTile) {
                if (0 < ((ColorPropertyTile) player.getProperties().get(i)).getNumHouses() ||
                        0 < ((ColorPropertyTile) player.getProperties().get(i)).getNumHotels()) {
                    playerOptions.add("Sell House/Hotel");
                    break;
                }
            }
        }
        playerOptions.add("Declare Bankruptcy");
        presenter.showPlayerOptions(playerOptions, player);
    }

    @Override
    public void getMortgageableProperties(Player player) {
        ArrayList<Property> mortgageableProperties = new ArrayList<>();
        //Goes through the list of properties the player owns and if it's not mortgaged it adds the name
        // to the mortgageableProperties list.
        for(int i = 0; i < player.getProperties().size(); i++){
            if(!player.getProperties().get(i).isMortgaged()){
                mortgageableProperties.add(player.getProperties().get(i));
            }
        }
        presenter.showMortgageableProperties(mortgageableProperties, player);
    }

    @Override
    public void getPropertiesWithHouses(Player player) {
        ArrayList<ColorPropertyTile> propertiesWithHouses = new ArrayList<>();
        //Goes through the list of properties the player owns and if it's not mortgaged it adds the name
        // to the mortgageableProperties list.
        for(int i = 0; i < player.getProperties().size(); i++){
            if(player.getProperties().get(i) instanceof ColorPropertyTile) {
                ColorPropertyTile propertyTile = (ColorPropertyTile) player.getProperties().get(i);
                if (0 < propertyTile.getNumHouses()) {
                    if(propertyDoesNotHaveLessHouses(player, propertyTile)){
                        propertiesWithHouses.add(propertyTile);
                    }
                }
            }
        }
        presenter.showPropertiesWithHouses(propertiesWithHouses, player);
    }

    private static boolean propertyDoesNotHaveLessHouses(Player player, ColorPropertyTile property){
        for(int i = 0; i < player.getProperties().size(); i++){
            int propertyHouses = property.getNumHouses() + property.getNumHotels();
            //Checks if this property is a color property (can't use colorPropertyTile methods until
            //I know it's a colorProperty
            if(player.getProperties().get(i) instanceof ColorPropertyTile){
                ColorPropertyTile property1 = (ColorPropertyTile) player.getProperties().get(i);
                int property1Houses = property1.getNumHouses() + property1.getNumHotels();
                if(property1.getColor().equals(property.getColor()) &&
                        propertyHouses < property1Houses){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void bankruptcy(Player bankruptPlayer, Player owedPlayer, GameState gameState) {
        if(owedPlayer == null){
            for(int i = 0; i < bankruptPlayer.getProperties().size(); i++){
                Property property = bankruptPlayer.getProperties().get(i);
                if(property.isMortgaged()){
                    property.unmortgage();
                }
                if(property instanceof ColorPropertyTile){
                    ((ColorPropertyTile) property).subtractHotel(((ColorPropertyTile) property).getNumHotels());
                    ((ColorPropertyTile) property).subtractHouse(((ColorPropertyTile) property).getNumHouses());
                }
                property.setOwner(null);
            }
            //gameState.removePlayer(bankruptPlayer);

        }
        else {
            owedPlayer.addMoney(bankruptPlayer.getMoney());
            for (int i = 0; i < bankruptPlayer.getProperties().size(); i++) {
                //If the owedPlayer inherites a mortgaged property they will have to unmortgage it on
                //their own turn
                bankruptPlayer.getProperties().get(i).setOwner(owedPlayer);

            }
            //gameState.removePlayer(bankruptPlayer);
        }
        presenter.showTransferOfAssets(bankruptPlayer, owedPlayer);

    }
}
