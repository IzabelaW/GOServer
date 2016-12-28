package Game;

import GameSummary.AreaMarker;
import Listeners.IPlayerMadeGameDecisionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
Class which represents Bot in Server.
 Bot is a single thread and implements interface IPlayer.
 */

public class Bot extends BotProxy{
    /**
     * Default login of bot
     */
    Login login;
    PlayerColor playerColor;
    IPlayer opponent;
    Random random = new Random();

    public Bot(){
        login = new Login("BOT");
        playerColor = PlayerColor.WHITE;
    }

    @Override
    public void makeGameDecision(IPlayerMadeGameDecisionListener listener) throws IOException {
        int x = random.nextInt(19);
        int y = random.nextInt(19);
        Turn turn = new Turn(x, y, playerColor);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listener.playerMadeTurn(this, turn);
    }

    @Override
    public Login getLogin() {
        return login;
    }

    @Override
    public IPlayer getOpponent() {
        return opponent;
    }

    @Override
    public void sendMyLogin() {

    }

    @Override
    public void sendInfoOpponentPassed() {
        opponent.sendInfo("MARK_DEAD");
    }

    @Override
    public void sendUpdatedBoard(ArrayList<String> updatedBoard) {}

    @Override
    public void sendInfoIllegalMoveKO() {}

    @Override
    public void sendInfoIllegalMoveSuicide() {}

    @Override
    public void sendInfoIllegalMoveOccupiedField() {}

    @Override
    public void sendInfoLegalMove() {}

    @Override
    public void sendInfoCapturedStones(String capturedForWhite, String capturedForBlack) {}

    @Override
    public void sendInfoMarkDeadStones() throws IOException {}

    @Override
    public void setIfOpponentPassed(boolean ifPassed) {

    }

    @Override
    public void sendInfo(String info) {}

    @Override
    public void sumUp(IPlayerMadeGameDecisionListener listener) throws IOException {}

    @Override
    public void markArea(IPlayerMadeGameDecisionListener listener) throws IOException {}

    @Override
    public void setAreaMarker(AreaMarker areaMarker) {}

    @Override
    public void disconnectPlayer() {}

    @Override
    public void deleteRoom() {}

    @Override
    public void setOpponent(IPlayer opponent) {
        this.opponent = opponent;
    }

}