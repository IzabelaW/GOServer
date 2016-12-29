package GameTests;

import Game.Game;
import Game.Room;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kasia on 2016-12-29.
 */
public class GameTest {
    Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void startGame() throws Exception {

    }

    @Test
    public void testOnCreateNewRoom() throws Exception {
        game.onCreateNewRoom(new Room());
        game.onCreateNewRoom(null);
        game.onCreateNewRoom(new Room());



    }

    @Test
    public void onJoinHumanToRoom() throws Exception {

    }

    @Test
    public void getListOfIndexesToString() throws Exception {

    }

    @Test
    public void getListOfInitiatorsLoginsToString() throws Exception {

    }

    @Test
    public void getListOfJoinersLoginsToString() throws Exception {

    }

    @Test
    public void onDeleteRoom() throws Exception {

    }

}