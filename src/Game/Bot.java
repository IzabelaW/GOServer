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
    private Login login;
    private PlayerColor playerColor;
    private IPlayer opponent;
    private Random random = new Random();

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
    public Login getLogin() { return login; }

    @Override
    public IPlayer getOpponent() {
        return opponent;
    }

    @Override
    public void sendInfoOpponentPassed() {
        opponent.sendInfo("MARK_DEAD");
    }

    @Override
    public void setOpponent(IPlayer opponent) {
        this.opponent = opponent;
    }

}