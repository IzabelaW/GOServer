package GameSummary;

import Game.Board;
import Game.PlayerColor;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Izabela on 2016-12-18.
 */
public class AreaMarker {
    private Board board;
    private PlayerColor[][] boardTab;
    private int[][] markedArea;

    public AreaMarker (Board board){
        this.board = board;
    }

    public ArrayList<String> markArea(int x, int y){
        boardTab = board.getBoard();
        markedArea = new int[19][19];
        flood_fill(x,y,1);

        return markedAreaToString();
    }

    public void flood_fill(int x, int y, int number){
        if (x < 0 || x >= 19 || y < 0 || y >= 19) return;

        if (boardTab[x][y].equals(PlayerColor.BLACK) || boardTab[x][y].equals(PlayerColor.WHITE)) return;

        if (markedArea[x][y] <= number && markedArea[x][y] != 0) return;

        if (boardTab[x][y].equals(PlayerColor.FREE))
        {
            markedArea[x][y] = number;

            flood_fill(x-1,   y,   number + 1);
            flood_fill(x+1,   y,   number + 1);
            flood_fill(     x,y+1, number + 1);
            flood_fill(     x,y-1, number + 1);
        }
    }

    private ArrayList<String> markedAreaToString(){
        ArrayList<String> markedAreaToString = new ArrayList<>();
        for (int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                markedAreaToString.add(Integer.toString(markedArea[i][j]));
            }
        }
        return markedAreaToString;
    }
}
