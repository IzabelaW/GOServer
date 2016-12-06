package Game;

import Listeners.IPlayerMadeTurnListener;

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
    public void makeTurn(IPlayerMadeTurnListener listener) {

    }

    public Login getLogin(){
        return login;
    }
}