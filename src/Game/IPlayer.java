package Game;

import Listeners.IPlayerMadeGameDecisionListener;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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

    void sendMyLogin();

    void sendInfoOpponentPassed();

    void sendInfoOpponentGaveUp();

    void sendUpdatedBoard(ArrayList<String> updatedBoard);

    void sendInfoIllegalMoveKO();

    void sendInfoIllegalMoveSuicide();

    void sendInfoIllegalMoveOccupiedField();

    void sendInfoLegalMove();

    void sendInfoCapturedStones(String capturedForWhite, String capturedForBlack);

    void sendInfoMarkDeadStones();

    void setIfOpponentPassed(boolean ifPassed);

    void sendInfo(String info);

    void sumUp() throws IOException;

}
