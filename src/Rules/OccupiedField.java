package Rules;

import Game.PlayerColor;
import Game.Turn;

/**
 * Created by Kasia on 2016-12-09.
 */
public class OccupiedField implements Rule {
    @Override
    public boolean check(PlayerColor[][] board, Turn turn) {
        return (board[turn.getX()][turn.getY()].equals(PlayerColor.WHITE) ||
                board[turn.getX()][turn.getY()].equals(PlayerColor.BLACK));
    }
}
