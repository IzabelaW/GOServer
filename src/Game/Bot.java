package Game;

import Listeners.IPlayerMadeGameDecisionListener;

import java.util.ArrayList;

/**
Class which represents Bot in Server.
 Bot is a single thread and implements interface IPlayer.
 */

public class Bot extends Thread implements IPlayer {
    /**
     * Default login of bot
     */
    Login login;

    @Override
    public void makeGameDecision(IPlayerMadeGameDecisionListener listener) {

    }

    public Login getLogin(){
        return login;
    }

    @Override
    public IPlayer getOpponent() {
        return null;
    }

    @Override
    public void opponentPassed() {

    }

    @Override
    public void opponentGaveUp() {

    }

    @Override
    public void sendUpdatedBoard(ArrayList<String> updatedBoard) {

    }

    @Override
    public void setOpponent(IPlayer opponent) {

    }
}