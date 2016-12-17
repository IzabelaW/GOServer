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
    public void sendInfoOpponentPassed() {

    }

    @Override
    public void sendInfoOpponentGaveUp() {

    }

    @Override
    public void sendUpdatedBoard(ArrayList<String> updatedBoard) {

    }

    @Override
    public void sendInfoIllegalMoveKO() {

    }

    @Override
    public void sendInfoIllegalMoveSuicide() {

    }

    @Override
    public void sendInfoIllegalMoveOccupiedField() {

    }

    @Override
    public void sendInfoLegalMove() {

    }

    @Override
    public void setOpponent(IPlayer opponent) {

    }

    @Override
    public void sendMyLogin() {

    }

    public void sendInfoCapturedStones(String capturedForWhite, String capturedForBlack){}

    public void sendInfoMarkDeadStones(){}

    public void setIfOpponentPassed(boolean ifPassed){}

    public void sendInfo(String info){}
}