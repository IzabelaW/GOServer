package Listeners;

import Game.Human;
import Game.Login;

/**
 * Created by Izabela on 2016-12-01.
 */
public interface IHumanStartedGameListener {

    void humanLogged(Human human, Login login);

    void humanChoseOpponent(Human human, String response);

    void humanDecidedIfNewRoom(Human human, String response);

    void humanChoseRoom(Human human, int indexOfRoom);

    void humanExited(int indexOfRoom, Login login);
}
