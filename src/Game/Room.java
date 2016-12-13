package Game;

import Listeners.IPlayerMadeGameDecisionListener;
import Rules.Ko;
import Rules.OccupiedField;
import Rules.Rule;
import Rules.Suicide;
import sun.nio.cs.KOI8_R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Room implements IPlayerMadeGameDecisionListener {
    private Board board;
    private IPlayer initiator;
    private IPlayer joiner;
    private int index;

    public Room(IPlayer initiator){
        this.initiator = initiator;
        board = new Board();
    }

    public IPlayer getInitiator() {
        return initiator;
    }

    public IPlayer getJoiner() { return joiner; }

    public void setJoiner(IPlayer joiner){
        this.joiner = joiner;
    }

    @Override
    public void playerMadeTurn(IPlayer player, Turn turn){

        Rule rule = board.analyzeTurn(turn);

        if (rule instanceof OccupiedField){
            player.sendInfoIllegalMoveOccupiedField();
            try {
                player.makeGameDecision(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (rule instanceof Suicide){
            player.sendInfoIllegalMoveSuicide();
            try {
                player.makeGameDecision(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (rule instanceof Ko){
            player.sendInfoIllegalMoveKO();
            try {
                player.makeGameDecision(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

            ArrayList<String> updatedBoard = boardToString(board.getBoard());

            player.sendInfoLegalMove();
            player.sendUpdatedBoard(updatedBoard);
            player.getOpponent().sendUpdatedBoard(updatedBoard);

            try {
                player.getOpponent().makeGameDecision(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void playersPassed() {

    }

    public void setIndex(int index) {
        this.index = index+1;
    }

    public int getIndex() {
        return index;
    }

    private ArrayList<String> boardToString(PlayerColor[][] board){
        ArrayList<String> updatedBoard = new ArrayList<>();

        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                updatedBoard.add(board[i][j].toString());
            }
        }

        return updatedBoard;
    }
}