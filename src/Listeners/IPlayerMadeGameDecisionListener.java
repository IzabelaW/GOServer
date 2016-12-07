package Listeners;

import Game.IPlayer;
import Game.Turn;

/**
 * Created by Kasia on 2016-11-29.
 */
public interface IPlayerMadeGameDecisionListener {

    void playerMadeTurn(IPlayer player, Turn turn);

    void playersPassed();

}
