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

    void sendInfoOpponentPassed();

    void sendInfoOpponentGaveUp();

    void sendUpdatedBoard(ArrayList<String> updatedBoard);

    void sendInfoIllegalMoveKO();

    void sendInfoIllegalMoveSuicide();

    void sendInfoIllegalMoveOccupiedField();

    void sendInfoLegalMove();

}
