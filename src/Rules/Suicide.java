package Rules;

import Game.PlayerColor;
import Game.Turn;

/**
 * Created by Kasia on 2016-12-09.
 */
public class Suicide implements Rule {
    @Override
    public boolean check(PlayerColor[][] playerColors, Turn turn) {
        return false;
    }
}
