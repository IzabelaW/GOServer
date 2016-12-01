package Game;

import Listeners.IPlayerMadeTurnListener;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Room implements IPlayerMadeTurnListener {
    private Board board;
    private IPlayer player;
    private IPlayer opponent;
    private int index;

    public Room(IPlayer player){
        this.player = player;
    }

    public void setOpponent(IPlayer opponent){
        this.opponent = opponent;
    }

    public void turnForPlayer(IPlayer player){
        player.makeTurn(this);
    }



    public void playerMadeTurn(IPlayer player, Turn turn){
        //odebranie odpowiedzi od playera, ktory player wykonal ruch i jaki
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
