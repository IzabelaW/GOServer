package Game;

import Listeners.IPlayerMadeTurnListener;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Bot extends Thread implements IPlayer {
    Login login;
    @Override
    public void makeTurn(IPlayerMadeTurnListener listener) {

    }

    public Login getLogin(){
        return login;
    }
}