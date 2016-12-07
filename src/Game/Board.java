package Game;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Board {

    private PlayerColor[][] board = new PlayerColor[19][19];

    public void analyzeTurn(Turn turn){
        updateBoard(turn);
    }

    private void updateBoard(Turn turn){
        int x = turn.getX();
        int y = turn.getY();
        PlayerColor playerColor = turn.getPlayerColor();
        board[x][y] = playerColor;
    }

    public PlayerColor[][] getBoard(){
        return board;
    }


}
