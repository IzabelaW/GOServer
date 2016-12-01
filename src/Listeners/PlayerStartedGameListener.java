package Listeners;

import Game.IPlayer;
import Game.Login;

/**
 * Created by Izabela on 2016-12-01.
 */
public interface PlayerStartedGameListener {
    void playerLogged(IPlayer player, Login login);

    void playerChoseOpponent(IPlayer player, String response);

    void playerDecidedIfNewRoom(IPlayer player, String response);

    void playerChoseRoom(IPlayer player, int numberOfRoom);
}
