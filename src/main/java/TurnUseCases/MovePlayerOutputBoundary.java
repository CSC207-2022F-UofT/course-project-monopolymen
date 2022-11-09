package TurnUseCases;

public interface MovePlayerOutputBoundary {
    /**
     *
     * @param choicesPlayerCanMake String array of the options the player can make
     * @param inJail boolean variable for weather the player is in jail
     */
    public void showChoices(String[] choicesPlayerCanMake, boolean inJail);
        /** Important, this method will take the inJail parameter and use it to determine if it should display
         showResultOfAction first or after the choices have been shown in the UI, for example, if the player was
         not in jail, it would prompt the result first, but if the player was in jail, it would prompt the choices
         **/

    /**
     * Displays the result or the action, such as rolling the dice and moving
     */
    public void showResultOfAction();

}
