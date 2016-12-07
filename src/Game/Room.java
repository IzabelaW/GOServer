package Game;

import Listeners.IPlayerMadeGameDecisionListener;

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

    public void turnForPlayer(IPlayer player){
        player.makeGameDecision(this);
    }

    @Override
    public void playerMadeTurn(IPlayer player, Turn turn){
        //odebranie odpowiedzi od playera, ktory initiator wykonal ruch i jaki

        ArrayList<String> updatedBoard = boardToString(board.getBoard());

        board.analizeTurn(turn);

        player.sendUpdatedBoard(updatedBoard);
        player.getOpponent().sendUpdatedBoard(updatedBoard);

        turnForPlayer(player.getOpponent());
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

    @Override
    public String toString(){
        if (joiner != null)
            return index + " " + initiator.getLogin().toString() + " " + joiner.getLogin().toString();
        else
            return index + " " + initiator.getLogin().toString() + " " + "-";
    }

    private ArrayList<String> boardToString(PlayerColor[][] board){
        ArrayList<String> updatedBoard = new ArrayList<>();

        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                updatedBoard.add(i + " " + j + board[i][j]);
            }
        }

        return updatedBoard;
    }
}


