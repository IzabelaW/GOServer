package Game;

import Listeners.PlayerMadeTurnListener;

/**
 * Created by Kasia on 2016-11-29.
 */
public interface IPlayer {

    void makeTurn(PlayerMadeTurnListener listener);
}
