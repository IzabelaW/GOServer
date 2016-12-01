package Game;

import Listeners.IPlayerMadeTurnListener;

/**
 * Created by Kasia on 2016-11-29.
 */
public interface IPlayer {

    void makeTurn(IPlayerMadeTurnListener listener);
}
