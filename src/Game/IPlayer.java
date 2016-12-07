package Game;

import Listeners.IPlayerMadeGameDecisionListener;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kasia on 2016-11-29.
 */
public interface IPlayer {

    void makeGameDecision(IPlayerMadeGameDecisionListener listener) throws IOException;

    Login getLogin();

    IPlayer getOpponent();

    void setOpponent(IPlayer opponent);

    void opponentPassed();

    void opponentGaveUp();

    void sendUpdatedBoard(ArrayList<String> updatedBoard);

}
