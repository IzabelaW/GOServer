package Game;

import Listeners.PlayerMadeTurnListener;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Room implements PlayerMadeTurnListener {
    Board board;
    IPlayer player1;
    IPlayer player2;

    public Room(IPlayer p1, IPlayer p2){
        player1 = p1;
        player2 = p2;
    }

    public void turnForPlayer(IPlayer player){
        player.makeTurn(this);
    }



    public void PlayerMadeTurn(IPlayer player, Turn turn){
        //odebranie odpowiedzi od playera, ktory player wykonal ruch i jaki
    }
}
