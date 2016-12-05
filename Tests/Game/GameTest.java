package Game;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by Izabela on 2016-12-01.
 */
public class GameTest {

    private Game game;
    private Human human;
    private Human opponent;
    private Socket socket;

    @Before
    public void setUp() throws Exception {
        ServerSocket listener = new ServerSocket(4000);
        socket = listener.accept();
        game = new Game();
        human = new Human(socket);
        opponent = new Human(socket);
    }

    @Test
    public void startGame() throws Exception {

    }

    @Test
    public void humanLogged() throws Exception {
        Gson gson = new Gson();
        Login login = gson.fromJson("login", Login.class);
        Login login1 = gson.fromJson("login", Login.class);
        game.humanLogged(human, login);
        assertEquals(human.getLogin(),login1);
        assertNotNull(login);
    }

    @Test
    public void humanChoseOpponent() throws Exception {
        game.humanChoseOpponent(human,"BOT");

    }

    @Test
    public void humanDecidedIfNewRoom() throws Exception {

    }

    @Test
    public void humanChoseRoom() throws Exception {
        Room room = new Room(human);
        opponent.chooseRoom(game);
       // IPlayer opponent1 = room.getOpponent();

    }

    @After
    public void tearDown(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}