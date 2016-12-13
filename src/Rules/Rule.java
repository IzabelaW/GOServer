package Rules;

import Game.PlayerColor;
import Game.Turn;

/**
 * Created by Kasia on 2016-12-09.
 */
public interface Rule {

    public boolean check(PlayerColor[][] board, Turn turn);
}
